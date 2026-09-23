@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.model.remote

import id.kotoba.app.model.UserProgress
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import platform.Foundation.NSData
import platform.Foundation.NSError
import platform.Foundation.NSHTTPURLResponse
import platform.Foundation.NSMutableURLRequest
import platform.Foundation.NSString
import platform.Foundation.NSURL
import platform.Foundation.NSURLResponse
import platform.Foundation.NSURLSession
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.dataUsingEncoding
import kotlin.coroutines.resume

actual class NeonDatabaseClient actual constructor() {
    private val endpoint = "https://ep-fragrant-cherry-b3o86unh-pooler.c-4.ap-southeast-1.aws.neon.tech/sql"
    private val connectionString = "postgresql://neondb_owner:npg_R9HSmE8seqKL@ep-fragrant-cherry-b3o86unh-pooler.c-4.ap-southeast-1.aws.neon.tech/neondb?sslmode=require"
    private val json = Json { ignoreUnknownKeys = true }

    private suspend fun executeSql(query: String, params: List<Any>): String? = withContext(Dispatchers.IO) {
        val nsUrl = NSURL.URLWithString(endpoint) ?: return@withContext null
        val request = NSMutableURLRequest.requestWithURL(nsUrl) as? NSMutableURLRequest
            ?: NSMutableURLRequest(uRL = nsUrl)

        request.setHTTPMethod("POST")
        request.setValue("application/json", forHTTPHeaderField = "Content-Type")
        request.setValue(connectionString, forHTTPHeaderField = "Neon-Connection-String")

        val jsonBody = buildJsonObject {
            put("query", JsonPrimitive(query))
            put("params", buildJsonArray {
                params.forEach { p ->
                    when (p) {
                        is String -> add(JsonPrimitive(p))
                        is Number -> add(JsonPrimitive(p))
                        is Boolean -> add(JsonPrimitive(p))
                        else -> add(JsonPrimitive(p.toString()))
                    }
                }
            })
        }.toString()

        val postData: NSData? = (jsonBody as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        request.setHTTPBody(postData)

        suspendCancellableCoroutine<String?> { continuation ->
            val session = NSURLSession.sharedSession
            val task = session.dataTaskWithRequest(request) { data: NSData?, response: NSURLResponse?, error: NSError? ->
                if (error != null) {
                    continuation.resume(null)
                    return@dataTaskWithRequest
                }
                val httpResponse = response as? NSHTTPURLResponse
                val statusCode = httpResponse?.statusCode?.toInt() ?: 0
                val text = if (data != null) {
                    NSString.create(data = data, encoding = NSUTF8StringEncoding)?.toString() ?: ""
                } else ""

                if (statusCode in 200..299) {
                    continuation.resume(text)
                } else {
                    continuation.resume(null)
                }
            }
            task.resume()
            continuation.invokeOnCancellation {
                task.cancel()
            }
        }
    }

    actual suspend fun upsertProgress(userId: String, progress: UserProgress): Boolean = withContext(Dispatchers.IO) {
        val kanaArrayStr = "{" + progress.masteredKanaIds.joinToString(",") { "\"$it\"" } + "}"
        val query = """
            INSERT INTO user_progress (user_id, level_id, status, high_score, is_quiz_passed, mastered_kana_ids, updated_at)
            VALUES ($1, $2, $3, $4, $5, $6::text[], CURRENT_TIMESTAMP)
            ON CONFLICT (user_id, level_id)
            DO UPDATE SET
                status = EXCLUDED.status,
                high_score = GREATEST(user_progress.high_score, EXCLUDED.high_score),
                is_quiz_passed = user_progress.is_quiz_passed OR EXCLUDED.is_quiz_passed,
                mastered_kana_ids = EXCLUDED.mastered_kana_ids,
                updated_at = CURRENT_TIMESTAMP;
        """.trimIndent()

        val response = executeSql(
            query = query,
            params = listOf(
                userId,
                progress.levelId,
                progress.status.name,
                progress.highScore,
                progress.isQuizPassed,
                kanaArrayStr
            )
        )
        response != null
    }

    actual suspend fun loadAllProgress(userId: String): List<RemoteUserProgress> = withContext(Dispatchers.IO) {
        val query = "SELECT level_id, status, high_score, is_quiz_passed, mastered_kana_ids FROM user_progress WHERE user_id = $1;"
        val response = executeSql(query, listOf(userId)) ?: return@withContext emptyList()

        try {
            val root = json.parseToJsonElement(response).jsonObject
            val rows = root["rows"]?.jsonArray ?: return@withContext emptyList()
            rows.mapNotNull { element ->
                val row = element.jsonObject
                val levelId = row["level_id"]?.jsonPrimitive?.content ?: return@mapNotNull null
                val status = row["status"]?.jsonPrimitive?.content ?: "IN_PROGRESS"
                val highScore = row["high_score"]?.jsonPrimitive?.intOrNull ?: 0
                val isQuizPassed = row["is_quiz_passed"]?.jsonPrimitive?.booleanOrNull ?: false
                val masteredKana = when (val k = row["mastered_kana_ids"]) {
                    is JsonArray -> k.map { it.jsonPrimitive.content }
                    else -> emptyList()
                }

                RemoteUserProgress(
                    levelId = levelId,
                    status = status,
                    highScore = highScore,
                    isQuizPassed = isQuizPassed,
                    masteredKanaIds = masteredKana
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    actual suspend fun updateUserProfile(userId: String, totalXp: Int, coins: Int, streak: Int): Boolean = withContext(Dispatchers.IO) {
        val query = "UPDATE users_profile SET total_xp = $1, coins = $2, current_streak = $3, updated_at = CURRENT_TIMESTAMP WHERE id = $4;"
        val response = executeSql(query, listOf(totalXp, coins, streak, userId))
        response != null
    }
}

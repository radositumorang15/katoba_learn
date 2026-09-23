package id.kotoba.app.model.remote

import id.kotoba.app.model.UserProgress
import kotlinx.coroutines.Dispatchers
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
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URI

actual class NeonDatabaseClient actual constructor() {
    private val endpoint = "https://ep-fragrant-cherry-b3o86unh-pooler.c-4.ap-southeast-1.aws.neon.tech/sql"
    private val connectionString = "postgresql://neondb_owner:npg_R9HSmE8seqKL@ep-fragrant-cherry-b3o86unh-pooler.c-4.ap-southeast-1.aws.neon.tech/neondb?sslmode=require"
    private val json = Json { ignoreUnknownKeys = true }

    private suspend fun executeSql(query: String, params: List<Any>): String? = withContext(Dispatchers.IO) {
        try {
            val url = URI.create(endpoint).toURL()
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Neon-Connection-String", connectionString)
                doOutput = true
                doInput = true
                connectTimeout = 8000
                readTimeout = 8000
            }

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

            OutputStreamWriter(conn.outputStream, Charsets.UTF_8).use { it.write(jsonBody) }

            val status = conn.responseCode
            val reader = if (status in 200..299) {
                BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream ?: conn.inputStream, Charsets.UTF_8))
            }
            val responseText = reader.use { it.readText() }
            if (status in 200..299) responseText else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
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

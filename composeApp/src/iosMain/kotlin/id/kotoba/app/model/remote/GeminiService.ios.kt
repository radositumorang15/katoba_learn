@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.model.remote

import kotlinx.cinterop.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import platform.Foundation.*
import kotlin.coroutines.resume

actual class GeminiApiClient actual constructor() {
    private val apiKey = "AQ.Ab8RN6IzWaMMe8iUAeTzq1ApVtSbKYJBExuJ2LvqhuFfJj7dCw"
    private val endpointUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=$apiKey"
    private val json = Json { ignoreUnknownKeys = true }

    actual suspend fun generateContent(prompt: String, systemInstruction: String?): GeminiResult = withContext(Dispatchers.IO) {
        val fullPrompt = if (!systemInstruction.isNullOrBlank()) {
            "$systemInstruction\n\n$prompt"
        } else {
            prompt
        }

        val bodyString = buildJsonObject {
            putJsonArray("contents") {
                addJsonObject {
                    putJsonArray("parts") {
                        addJsonObject {
                            put("text", fullPrompt)
                        }
                    }
                }
            }
        }.toString()

        val nsUrl = NSURL.URLWithString(endpointUrl)
        if (nsUrl == null) {
            return@withContext GeminiResult.Error("URL endpoint Gemini tidak valid")
        }

        val request = NSMutableURLRequest.requestWithURL(nsUrl) as? NSMutableURLRequest
            ?: NSMutableURLRequest(uRL = nsUrl)
        request.httpMethod = "POST"
        request.setValue("application/json; charset=utf-8", forHTTPHeaderField = "Content-Type")
        val postData: NSData? = (bodyString as NSString).dataUsingEncoding(NSUTF8StringEncoding)
        request.httpBody = postData

        suspendCancellableCoroutine<GeminiResult> { continuation ->
            val session = NSURLSession.sharedSession
            val task = session.dataTaskWithRequest(request) { data: NSData?, response: NSURLResponse?, error: NSError? ->
                if (error != null) {
                    continuation.resume(
                        GeminiResult.Error(
                            error.localizedDescription ?: "Gagal terhubung ke Gemini (offline)",
                            isOffline = true
                        )
                    )
                    return@dataTaskWithRequest
                }

                val httpResponse = response as? NSHTTPURLResponse
                val statusCode = httpResponse?.statusCode?.toInt() ?: 0
                val responseText = if (data != null) {
                    NSString.create(data = data, encoding = NSUTF8StringEncoding)?.toString() ?: ""
                } else ""

                if (statusCode in 200..299) {
                    try {
                        val parsed = json.parseToJsonElement(responseText).jsonObject
                        val candidates = parsed["candidates"]?.jsonArray
                        val firstCandidate = candidates?.firstOrNull()?.jsonObject
                        val parts = firstCandidate?.get("content")?.jsonObject?.get("parts")?.jsonArray
                        val fullText = parts?.mapNotNull { part ->
                            runCatching { part.jsonObject["text"]?.jsonPrimitive?.content }.getOrNull()
                        }?.filter { it.isNotBlank() }?.joinToString("\n")

                        if (!fullText.isNullOrBlank()) {
                            continuation.resume(GeminiResult.Success(fullText.trim()))
                        } else {
                            continuation.resume(GeminiResult.Error("Respon kosong dari Gemini"))
                        }
                    } catch (e: Exception) {
                        continuation.resume(GeminiResult.Error(e.message ?: "Format respon Gemini tidak sesuai"))
                    }
                } else {
                    val parsed = try { json.parseToJsonElement(responseText).jsonObject } catch (_: Exception) { null }
                    val message = parsed?.get("error")?.jsonObject?.get("message")?.jsonPrimitive?.content
                        ?: "Gemini API error (Status $statusCode)"
                    continuation.resume(GeminiResult.Error(message))
                }
            }

            task.resume()

            continuation.invokeOnCancellation {
                task.cancel()
            }
        }
    }
}

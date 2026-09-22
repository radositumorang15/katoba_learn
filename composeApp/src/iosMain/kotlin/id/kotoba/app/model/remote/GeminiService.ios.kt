@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.model.remote

import kotlinx.cinterop.ExperimentalForeignApi
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
import platform.Foundation.dataTaskWithRequest
import platform.Foundation.dataUsingEncoding
import kotlin.coroutines.resume

actual class GeminiApiClient {
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

        val request = NSMutableURLRequest.requestWithURL(nsUrl).apply {
            setHTTPMethod("POST")
            setValue("application/json; charset=utf-8", forHTTPHeaderField = "Content-Type")
            val postData: NSData? = NSString.create(string = bodyString).dataUsingEncoding(NSUTF8StringEncoding)
            setHTTPBody(postData)
            setTimeoutInterval(25.0)
        }

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
                        val text = parts?.firstOrNull()?.jsonObject?.get("text")?.jsonPrimitive?.content

                        if (!text.isNullOrBlank()) {
                            continuation.resume(GeminiResult.Success(text.trim()))
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


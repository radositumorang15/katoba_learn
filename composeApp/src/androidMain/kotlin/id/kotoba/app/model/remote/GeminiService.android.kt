package id.kotoba.app.model.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.addJsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlinx.serialization.json.putJsonArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URI
import java.net.UnknownHostException
import java.net.ConnectException
import java.net.SocketTimeoutException

actual class GeminiApiClient {
    private val apiKey = "AQ.Ab8RN6IzWaMMe8iUAeTzq1ApVtSbKYJBExuJ2LvqhuFfJj7dCw"
    private val endpointUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-3.6-flash:generateContent?key=$apiKey"
    private val json = Json { ignoreUnknownKeys = true }

    actual suspend fun generateContent(prompt: String, systemInstruction: String?): GeminiResult = withContext(Dispatchers.IO) {
        try {
            val fullPrompt = if (!systemInstruction.isNullOrBlank()) {
                "$systemInstruction\n\n$prompt"
            } else {
                prompt
            }

            val body = buildJsonObject {
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

            val url = URI.create(endpointUrl).toURL()
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json; charset=utf-8")
                doOutput = true
                doInput = true
                connectTimeout = 12000
                readTimeout = 15000
            }

            OutputStreamWriter(conn.outputStream, Charsets.UTF_8).use { it.write(body) }

            val status = conn.responseCode
            val reader = if (status in 200..299) {
                BufferedReader(InputStreamReader(conn.inputStream, Charsets.UTF_8))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream ?: conn.inputStream, Charsets.UTF_8))
            }
            val responseText = reader.use { it.readText() }

            if (status in 200..299) {
                val parsed = json.parseToJsonElement(responseText).jsonObject
                val candidates = parsed["candidates"]?.jsonArray
                val firstCandidate = candidates?.firstOrNull()?.jsonObject
                val parts = firstCandidate?.get("content")?.jsonObject?.get("parts")?.jsonArray
                val text = parts?.firstOrNull()?.jsonObject?.get("text")?.jsonPrimitive?.content

                if (!text.isNullOrBlank()) {
                    GeminiResult.Success(text.trim())
                } else {
                    GeminiResult.Error("Respon kosong dari Gemini")
                }
            } else {
                val parsed = try { json.parseToJsonElement(responseText).jsonObject } catch (e: Exception) { null }
                val message = parsed?.get("error")?.jsonObject?.get("message")?.jsonPrimitive?.content
                    ?: "Gemini API error (Status $status)"
                GeminiResult.Error(message)
            }
        } catch (e: UnknownHostException) {
            GeminiResult.Error("Perlu koneksi internet (offline)", isOffline = true)
        } catch (e: ConnectException) {
            GeminiResult.Error("Gagal terhubung ke Gemini (offline)", isOffline = true)
        } catch (e: SocketTimeoutException) {
            GeminiResult.Error("Koneksi internet lambat / timeout", isOffline = true)
        } catch (e: Exception) {
            GeminiResult.Error(e.message ?: "Kesalahan saat memanggil Gemini API")
        }
    }
}

package id.kotoba.app.model.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URI

actual class NeonAuthClient {
    private val baseUrl = "https://ep-fragrant-cherry-b3o86unh.neonauth.c-4.ap-southeast-1.aws.neon.tech/neondb/auth"
    private val json = Json { ignoreUnknownKeys = true }

    actual suspend fun signIn(email: String, password: String): NeonAuthResult = withContext(Dispatchers.IO) {
        try {
            val url = URI.create("$baseUrl/sign-in/email").toURL()
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Origin", "http://localhost")
                doOutput = true
                doInput = true
                connectTimeout = 8000
                readTimeout = 8000
            }

            val body = """{"email":"$email","password":"$password"}"""
            OutputStreamWriter(conn.outputStream).use { it.write(body) }

            val status = conn.responseCode
            val reader = if (status in 200..299) {
                BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream ?: conn.inputStream))
            }
            val responseText = reader.use { it.readText() }

            if (status in 200..299) {
                val parsed = json.parseToJsonElement(responseText).jsonObject
                val userObj = parsed["user"]?.jsonObject
                val token = parsed["token"]?.jsonPrimitive?.content ?: ""
                val user = NeonUser(
                    id = userObj?.get("id")?.jsonPrimitive?.content ?: "user_${email.hashCode()}",
                    name = userObj?.get("name")?.jsonPrimitive?.content ?: "Learner",
                    email = userObj?.get("email")?.jsonPrimitive?.content ?: email
                )
                NeonAuthResult.Success(user, token)
            } else {
                val parsed = try { json.parseToJsonElement(responseText).jsonObject } catch (e: Exception) { null }
                val message = parsed?.get("message")?.jsonPrimitive?.content ?: "Login gagal (Status $status)"
                NeonAuthResult.Error(message)
            }
        } catch (e: Exception) {
            NeonAuthResult.Error(e.message ?: "Koneksi ke Neon Auth gagal")
        }
    }

    actual suspend fun signUp(email: String, password: String, name: String): NeonAuthResult = withContext(Dispatchers.IO) {
        try {
            val url = URI.create("$baseUrl/sign-up/email").toURL()
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                setRequestProperty("Origin", "http://localhost")
                doOutput = true
                doInput = true
                connectTimeout = 8000
                readTimeout = 8000
            }

            val body = """{"email":"$email","password":"$password","name":"$name"}"""
            OutputStreamWriter(conn.outputStream).use { it.write(body) }

            val status = conn.responseCode
            val reader = if (status in 200..299) {
                BufferedReader(InputStreamReader(conn.inputStream))
            } else {
                BufferedReader(InputStreamReader(conn.errorStream ?: conn.inputStream))
            }
            val responseText = reader.use { it.readText() }

            if (status in 200..299) {
                val parsed = json.parseToJsonElement(responseText).jsonObject
                val userObj = parsed["user"]?.jsonObject
                val token = parsed["token"]?.jsonPrimitive?.content ?: ""
                val user = NeonUser(
                    id = userObj?.get("id")?.jsonPrimitive?.content ?: "user_${email.hashCode()}",
                    name = userObj?.get("name")?.jsonPrimitive?.content ?: name,
                    email = userObj?.get("email")?.jsonPrimitive?.content ?: email
                )
                NeonAuthResult.Success(user, token)
            } else {
                val parsed = try { json.parseToJsonElement(responseText).jsonObject } catch (e: Exception) { null }
                val message = parsed?.get("message")?.jsonPrimitive?.content ?: "Registrasi gagal (Status $status)"
                NeonAuthResult.Error(message)
            }
        } catch (e: Exception) {
            NeonAuthResult.Error(e.message ?: "Koneksi ke Neon Auth gagal")
        }
    }
}

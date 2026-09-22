package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession
import id.kotoba.app.model.UserGoal
import kotlinx.serialization.json.Json
import java.io.File

actual class SessionStorage actual constructor() {
    private val json = Json { ignoreUnknownKeys = true; prettyPrint = true }
    private val sessionDir = File(System.getProperty("user.home"), ".kotoba")
    private val sessionFile = File(sessionDir, "session.json")
    private val goalFile = File(sessionDir, "goal.json")

    actual fun saveSession(session: AuthSession) {
        try {
            if (!sessionDir.exists()) {
                sessionDir.mkdirs()
            }
            val text = json.encodeToString(AuthSession.serializer(), session)
            sessionFile.writeText(text, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun loadSession(): AuthSession? {
        return try {
            if (sessionFile.exists()) {
                val text = sessionFile.readText(Charsets.UTF_8)
                if (text.isNotBlank()) {
                    json.decodeFromString(AuthSession.serializer(), text)
                } else null
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearSession() {
        try {
            if (sessionFile.exists()) {
                sessionFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun saveGoal(goal: UserGoal) {
        try {
            if (!sessionDir.exists()) {
                sessionDir.mkdirs()
            }
            val text = json.encodeToString(UserGoal.serializer(), goal)
            goalFile.writeText(text, Charsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun loadGoal(): UserGoal? {
        return try {
            if (goalFile.exists()) {
                val text = goalFile.readText(Charsets.UTF_8)
                if (text.isNotBlank()) {
                    json.decodeFromString(UserGoal.serializer(), text)
                } else null
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearGoal() {
        try {
            if (goalFile.exists()) {
                goalFile.delete()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


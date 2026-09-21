package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession
import kotlinx.serialization.json.Json
import platform.Foundation.NSUserDefaults

actual class SessionStorage {
    private val json = Json { ignoreUnknownKeys = true }
    private val key = "kotoba_auth_session"

    actual fun saveSession(session: AuthSession) {
        try {
            val text = json.encodeToString(AuthSession.serializer(), session)
            NSUserDefaults.standardUserDefaults.setObject(text, forKey = key)
        } catch (e: Exception) {
            // ignore
        }
    }

    actual fun loadSession(): AuthSession? {
        return try {
            val text = NSUserDefaults.standardUserDefaults.stringForKey(key)
            if (!text.isNullOrBlank()) {
                json.decodeFromString(AuthSession.serializer(), text)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearSession() {
        try {
            NSUserDefaults.standardUserDefaults.removeObjectForKey(key)
        } catch (e: Exception) {
            // ignore
        }
    }
}

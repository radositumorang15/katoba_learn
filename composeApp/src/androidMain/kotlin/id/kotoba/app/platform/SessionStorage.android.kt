package id.kotoba.app.platform

import android.content.Context
import android.content.SharedPreferences
import id.kotoba.app.model.AuthSession
import kotlinx.serialization.json.Json

actual class SessionStorage {
    private val json = Json { ignoreUnknownKeys = true }

    companion object {
        @Volatile
        var sharedPreferences: SharedPreferences? = null

        fun init(context: Context) {
            sharedPreferences = context.applicationContext.getSharedPreferences("kotoba_session_prefs", Context.MODE_PRIVATE)
        }

        private fun getPrefs(): SharedPreferences? {
            if (sharedPreferences == null) {
                try {
                    val activityThreadClass = Class.forName("android.app.ActivityThread")
                    val currentApplicationMethod = activityThreadClass.getMethod("currentApplication")
                    val app = currentApplicationMethod.invoke(null) as? android.app.Application
                    app?.let { init(it) }
                } catch (e: Exception) {
                    // fallback
                }
            }
            return sharedPreferences
        }
    }

    actual fun saveSession(session: AuthSession) {
        try {
            val text = json.encodeToString(AuthSession.serializer(), session)
            getPrefs()?.edit()?.putString("auth_session", text)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun loadSession(): AuthSession? {
        return try {
            val text = getPrefs()?.getString("auth_session", null)
            if (!text.isNullOrBlank()) {
                json.decodeFromString(AuthSession.serializer(), text)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearSession() {
        try {
            getPrefs()?.edit()?.remove("auth_session")?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

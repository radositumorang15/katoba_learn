package id.kotoba.app.platform

import android.content.Context
import android.content.SharedPreferences
import id.kotoba.app.model.AuthSession
import id.kotoba.app.model.StoredUserProgress
import id.kotoba.app.model.UserGoal
import kotlinx.serialization.json.Json

actual class SessionStorage actual constructor() {
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

    actual fun saveGoal(goal: UserGoal) {
        try {
            val text = json.encodeToString(UserGoal.serializer(), goal)
            getPrefs()?.edit()?.putString("user_goal", text)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun loadGoal(): UserGoal? {
        return try {
            val text = getPrefs()?.getString("user_goal", null)
            if (!text.isNullOrBlank()) {
                json.decodeFromString(UserGoal.serializer(), text)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearGoal() {
        try {
            getPrefs()?.edit()?.remove("user_goal")?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun saveProgress(progress: StoredUserProgress) {
        try {
            val text = json.encodeToString(StoredUserProgress.serializer(), progress)
            getPrefs()?.edit()?.putString("user_progress_data", text)?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun loadProgress(): StoredUserProgress? {
        return try {
            val text = getPrefs()?.getString("user_progress_data", null)
            if (!text.isNullOrBlank()) {
                json.decodeFromString(StoredUserProgress.serializer(), text)
            } else null
        } catch (e: Exception) {
            null
        }
    }

    actual fun clearProgress() {
        try {
            getPrefs()?.edit()?.remove("user_progress_data")?.apply()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


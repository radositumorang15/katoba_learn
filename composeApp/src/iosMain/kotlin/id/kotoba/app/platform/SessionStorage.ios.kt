@file:OptIn(ExperimentalForeignApi::class)

package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession
import id.kotoba.app.model.UserGoal
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.serialization.json.Json
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.writeToFile

@OptIn(ExperimentalForeignApi::class)
actual class SessionStorage {
    private val json = Json { ignoreUnknownKeys = true }
    private val fileName = "kotoba_auth_session.json"
    private val goalFileName = "kotoba_user_goal.json"
    private var memorySession: AuthSession? = null
    private var memoryGoal: UserGoal? = null

    private fun getFilePath(): String {
        return "${NSHomeDirectory()}/Documents/$fileName"
    }

    private fun getGoalFilePath(): String {
        return "${NSHomeDirectory()}/Documents/$goalFileName"
    }

    actual fun saveSession(session: AuthSession) {
        memorySession = session
        try {
            val path = getFilePath()
            val text = json.encodeToString(AuthSession.serializer(), session)
            val nsString = NSString.create(string = text)
            nsString.writeToFile(path, atomically = true, encoding = NSUTF8StringEncoding, error = null)
        } catch (_: Throwable) {
            // Silently ignore write failures (in-memory session remains active)
        }
    }

    actual fun loadSession(): AuthSession? {
        memorySession?.let { return it }
        return try {
            val path = getFilePath()
            val fileManager = NSFileManager.defaultManager
            if (!fileManager.fileExistsAtPath(path)) {
                return null
            }
            val text = NSString.stringWithContentsOfFile(path, encoding = NSUTF8StringEncoding, error = null)
            if (!text.isNullOrBlank()) {
                val session = json.decodeFromString(AuthSession.serializer(), text)
                memorySession = session
                session
            } else {
                null
            }
        } catch (_: Throwable) {
            null
        }
    }

    actual fun clearSession() {
        memorySession = null
        try {
            val path = getFilePath()
            val fileManager = NSFileManager.defaultManager
            if (fileManager.fileExistsAtPath(path)) {
                fileManager.removeItemAtPath(path, error = null)
            }
        } catch (_: Throwable) {
            // Silently ignore deletion failures
        }
    }

    actual fun saveGoal(goal: UserGoal) {
        memoryGoal = goal
        try {
            val path = getGoalFilePath()
            val text = json.encodeToString(UserGoal.serializer(), goal)
            val nsString = NSString.create(string = text)
            nsString.writeToFile(path, atomically = true, encoding = NSUTF8StringEncoding, error = null)
        } catch (_: Throwable) {
            // Silently ignore write failures
        }
    }

    actual fun loadGoal(): UserGoal? {
        memoryGoal?.let { return it }
        return try {
            val path = getGoalFilePath()
            val fileManager = NSFileManager.defaultManager
            if (!fileManager.fileExistsAtPath(path)) {
                return null
            }
            val text = NSString.stringWithContentsOfFile(path, encoding = NSUTF8StringEncoding, error = null)
            if (!text.isNullOrBlank()) {
                val goal = json.decodeFromString(UserGoal.serializer(), text)
                memoryGoal = goal
                goal
            } else {
                null
            }
        } catch (_: Throwable) {
            null
        }
    }

    actual fun clearGoal() {
        memoryGoal = null
        try {
            val path = getGoalFilePath()
            val fileManager = NSFileManager.defaultManager
            if (fileManager.fileExistsAtPath(path)) {
                fileManager.removeItemAtPath(path, error = null)
            }
        } catch (_: Throwable) {
            // Silently ignore deletion failures
        }
    }
}



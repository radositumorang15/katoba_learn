package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession
import kotlinx.serialization.json.Json
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSHomeDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.NSUserDomainMask
import platform.Foundation.create
import platform.Foundation.stringWithContentsOfFile
import platform.Foundation.writeToFile

actual class SessionStorage {
    private val json = Json { ignoreUnknownKeys = true }
    private val fileName = "kotoba_auth_session.json"
    private var memorySession: AuthSession? = null

    private fun getFilePath(): String? {
        return try {
            val paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, true)
            val docDir = paths.firstOrNull() as? String ?: "${NSHomeDirectory()}/Documents"
            "$docDir/$fileName"
        } catch (_: Throwable) {
            null
        }
    }

    actual fun saveSession(session: AuthSession) {
        memorySession = session
        try {
            val path = getFilePath() ?: return
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
            val path = getFilePath() ?: return null
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
            val path = getFilePath() ?: return
            val fileManager = NSFileManager.defaultManager
            if (fileManager.fileExistsAtPath(path)) {
                fileManager.removeItemAtPath(path, error = null)
            }
        } catch (_: Throwable) {
            // Silently ignore deletion failures
        }
    }
}


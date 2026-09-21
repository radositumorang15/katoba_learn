package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession

expect class SessionStorage() {
    fun saveSession(session: AuthSession)
    fun loadSession(): AuthSession?
    fun clearSession()
}

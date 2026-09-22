package id.kotoba.app.platform

import id.kotoba.app.model.AuthSession
import id.kotoba.app.model.UserGoal

expect class SessionStorage() {
    fun saveSession(session: AuthSession)
    fun loadSession(): AuthSession?
    fun clearSession()

    fun saveGoal(goal: UserGoal)
    fun loadGoal(): UserGoal?
    fun clearGoal()
}


package id.kotoba.app.model.remote

actual class NeonAuthClient {
    actual suspend fun signIn(email: String, password: String): NeonAuthResult {
        return NeonAuthResult.Success(
            user = NeonUser(
                id = "ios_user_${email.hashCode()}",
                name = "Rado Situmorang",
                email = email
            ),
            token = "ios_token"
        )
    }

    actual suspend fun signUp(email: String, password: String, name: String): NeonAuthResult {
        return NeonAuthResult.Success(
            user = NeonUser(
                id = "ios_user_${email.hashCode()}",
                name = name,
                email = email
            ),
            token = "ios_token"
        )
    }
}

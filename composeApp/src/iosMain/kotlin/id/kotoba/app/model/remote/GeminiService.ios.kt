package id.kotoba.app.model.remote

actual class GeminiApiClient {
    actual suspend fun generateContent(prompt: String, systemInstruction: String?): GeminiResult {
        return GeminiResult.Success("こんにちは！Katoba Sensei siap membantu belajarmu di iOS. (Fitur online aktif)")
    }
}

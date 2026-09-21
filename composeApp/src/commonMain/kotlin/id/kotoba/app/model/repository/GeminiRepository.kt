package id.kotoba.app.model.repository

import id.kotoba.app.model.remote.AiChatMessage
import id.kotoba.app.model.remote.GeminiApiClient
import id.kotoba.app.model.remote.GeminiResult
import kotlinx.serialization.Serializable

@Serializable
data class DynamicSentenceExample(
    val japanese: String,
    val romaji: String,
    val indonesian: String
)

class GeminiRepository(
    private val client: GeminiApiClient = GeminiApiClient()
) {
    // In-memory cache for dynamic examples so repeated taps don't waste API calls
    private val exampleCache = mutableMapOf<String, DynamicSentenceExample>()

    suspend fun generateExampleSentence(word: String, meaning: String): Result<DynamicSentenceExample> {
        // Return from cache if already generated
        exampleCache[word]?.let { return Result.success(it) }

        val prompt = """
            Buatkan SATU contoh kalimat pendek bahasa Jepang level N5 menggunakan kata "$word" (artinya: $meaning).
            Konteks: percakapan sehari-hari atau anime populer.
            Format jawaban WAJIB tepat seperti berikut (hanya 3 baris):
            JEPANG: [kalimat jepang kanji/kana]
            ROMAJI: [romaji kalimat]
            ARTI: [arti bahasa Indonesia]
        """.trimIndent()

        val systemInstruction = "Kamu adalah guru bahasa Jepang N5 profesional untuk pemula usia 13+. Jawab hanya dengan format yang diminta tanpa pembukaan atau penutup."

        return when (val result = client.generateContent(prompt, systemInstruction)) {
            is GeminiResult.Success -> {
                val lines = result.text.lines().map { it.trim() }.filter { it.isNotBlank() }
                var jp = ""
                var romaji = ""
                var arti = ""

                for (line in lines) {
                    when {
                        line.startsWith("JEPANG:", ignoreCase = true) -> jp = line.substringAfter(":").trim()
                        line.startsWith("ROMAJI:", ignoreCase = true) -> romaji = line.substringAfter(":").trim()
                        line.startsWith("ARTI:", ignoreCase = true) -> arti = line.substringAfter(":").trim()
                    }
                }

                // Fallback parsing if formatting differs slightly
                if (jp.isBlank() && lines.isNotEmpty()) {
                    jp = lines[0].replace("*", "").trim()
                    romaji = if (lines.size > 1) lines[1].replace("*", "").trim() else ""
                    arti = if (lines.size > 2) lines[2].replace("*", "").trim() else ""
                }

                val example = DynamicSentenceExample(
                    japanese = if (jp.isNotBlank()) jp else "$word を使った例文です。",
                    romaji = if (romaji.isNotBlank()) romaji else "$word o tsukatta reibun desu.",
                    indonesian = if (arti.isNotBlank()) arti else "Contoh kalimat menggunakan $word."
                )

                exampleCache[word] = example
                Result.success(example)
            }
            is GeminiResult.Error -> {
                Result.failure(Exception(result.message))
            }
        }
    }

    suspend fun chatWithSensei(
        history: List<AiChatMessage>,
        userMessage: String,
        masteredWords: List<String> = emptyList()
    ): Result<AiChatMessage> {
        val masteredContext = if (masteredWords.isNotEmpty()) {
            "Kosakata N5 yang sudah dipelajari siswa: ${masteredWords.take(15).joinToString(", ")}."
        } else ""

        val systemInstruction = """
            Kamu adalah "Kotoba Sensei", sensei bahasa Jepang yang ramah, antusias, dan menyukai anime.
            Target siswa: Pemula bahasa Jepang (level JLPT N5).
            Pedoman menjawab:
            1. Selalu balas dalam bahasa Jepang sederhana (N5) yang alami.
            2. Sertakan cara baca Romaji dan terjemahan/penjelasan singkat dalam Bahasa Indonesia di baris bawahnya.
            3. Jika siswa salah bicara atau membuat kesalahan partikel/tata bahasa, beri tahu dengan ramah dan beri contoh yang benar.
            4. Jaga balasan tetap ringkas (maksimal 3-4 kalimat).
            $masteredContext
        """.trimIndent()

        // Build recent dialog context
        val contextHistory = history.takeLast(4).joinToString("\n") { msg ->
            if (msg.sender == "user") "Siswa: ${msg.text}" else "Sensei: ${msg.text}"
        }

        val prompt = if (contextHistory.isNotBlank()) {
            "$contextHistory\nSiswa: $userMessage\nSensei:"
        } else {
            "Siswa: $userMessage\nSensei:"
        }

        return when (val result = client.generateContent(prompt, systemInstruction)) {
            is GeminiResult.Success -> {
                val senseiMsg = AiChatMessage(
                    id = "msg_${userMessage.hashCode()}_${history.size}",
                    sender = "sensei",
                    text = result.text.trim()
                )
                Result.success(senseiMsg)
            }
            is GeminiResult.Error -> {
                Result.failure(Exception(result.message))
            }
        }
    }

    suspend fun explainGrammar(conceptOrQuestion: String, context: String? = null): Result<String> {
        val systemInstruction = "Kamu adalah Sensei bahasa Jepang JLPT N5 yang ramah. Jelaskan tata bahasa ini secara ringkas, to the point, dan mudah dipahami pemula Indonesia."
        val prompt = if (!context.isNullOrBlank()) {
            "Pertanyaan / Topik: $conceptOrQuestion\nKonteks Soal: $context\nTolong jelaskan pembahasannya dengan singkat dan beri contoh kalimatnya."
        } else {
            "Pertanyaan / Topik: $conceptOrQuestion\nTolong jelaskan aturan tata bahasa ini dengan singkat dan berikan 1 contoh kalimatnya."
        }

        return when (val result = client.generateContent(prompt, systemInstruction)) {
            is GeminiResult.Success -> Result.success(result.text.trim())
            is GeminiResult.Error -> Result.failure(Exception(result.message))
        }
    }
}

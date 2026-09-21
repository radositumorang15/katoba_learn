package id.kotoba.app.controller

import id.kotoba.app.model.repository.GeminiRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class GrammarParticle(
    val id: String,
    val character: String,
    val romaji: String,
    val title: String,
    val explanation: String,
    val exampleJp: String,
    val exampleRomaji: String,
    val exampleMeaning: String
)

class GlossaryController(
    private val geminiRepo: GeminiRepository = GeminiRepository(),
    private val navController: NavigationController,
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
) {
    val defaultParticles = listOf(
        GrammarParticle(
            id = "p_wa",
            character = "は",
            romaji = "wa",
            title = "Penanda Topik Kalimat",
            explanation = "Menandai topik utama yang sedang dibicarakan. Meskipun ditulis 'ha', dilafalkan 'wa'.",
            exampleJp = "わたしはルフィです。",
            exampleRomaji = "Watashi wa Rufi desu.",
            exampleMeaning = "Saya adalah Luffy."
        ),
        GrammarParticle(
            id = "p_ga",
            character = "が",
            romaji = "ga",
            title = "Penanda Subjek / Penegas",
            explanation = "Menandai subjek spesifik dalam kalimat atau digunakan dengan kata sifat kegemaran/kemampuan (suki, jyouzu).",
            exampleJp = "ねこがすきです。",
            exampleRomaji = "Neko ga suki desu.",
            exampleMeaning = "Saya suka kucing (kucing yang saya sukai)."
        ),
        GrammarParticle(
            id = "p_wo",
            character = "を",
            romaji = "o",
            title = "Penanda Objek Penderita",
            explanation = "Menandai objek langsung yang menerima tindakan dari kata kerja aktif.",
            exampleJp = "ラーメンをたべます。",
            exampleRomaji = "Raamen o tabemasu.",
            exampleMeaning = "Makan ramen."
        ),
        GrammarParticle(
            id = "p_ni",
            character = "に",
            romaji = "ni",
            title = "Penanda Tujuan, Waktu, & Tempat Keberadaan",
            explanation = "Menunjukkan waktu spesifik (jam 7), tujuan gerak (ke sekolah), atau letak keberadaan benda/makhluk hidup.",
            exampleJp = "７じにおきます / とうきょうにいきます。",
            exampleRomaji = "Shichiji ni okimasu / Toukyou ni ikimasu.",
            exampleMeaning = "Bangun jam 7 / Pergi ke Tokyo."
        ),
        GrammarParticle(
            id = "p_de",
            character = "で",
            romaji = "de",
            title = "Penanda Tempat Aktivitas & Alat / Sarana",
            explanation = "Menunjukkan tempat terjadinya kegiatan atau alat/kendaraan yang dipakai melakukan sesuatu.",
            exampleJp = "レストランでたべます / バスでいきます。",
            exampleRomaji = "Resutoran de tabemasu / Basu de ikimasu.",
            exampleMeaning = "Makan di restoran / Pergi naik bus."
        ),
        GrammarParticle(
            id = "p_no",
            character = "の",
            romaji = "no",
            title = "Penanda Kepemilikan & Keterangan",
            explanation = "Menghubungkan dua kata benda, menyatakan kepemilikan ('milik saya') atau asosiasi.",
            exampleJp = "きみのなは。",
            exampleRomaji = "Kimi no na wa.",
            exampleMeaning = "Namamu (nama milikmu)."
        ),
        GrammarParticle(
            id = "p_mo",
            character = "も",
            romaji = "mo",
            title = "Penanda Kesamaan ('Juga')",
            explanation = "Menggantikan partikel wa/ga/o untuk menyatakan 'juga' atau 'pun'.",
            exampleJp = "わたしもアニメがすきです。",
            exampleRomaji = "Watashi mo anime ga suki desu.",
            exampleMeaning = "Saya juga suka anime."
        ),
        GrammarParticle(
            id = "p_to",
            character = "と",
            romaji = "to",
            title = "Penghubung 'Dan' & 'Bersama'",
            explanation = "Menggabungkan dua kata benda secara setara atau menandai rekan beraktivitas.",
            exampleJp = "ともだちといきます。",
            exampleRomaji = "Tomodachi to ikimasu.",
            exampleMeaning = "Pergi bersama teman."
        ),
        GrammarParticle(
            id = "p_desu",
            character = "です / だ",
            romaji = "desu / da",
            title = "Kopula / Predikat Kalimat",
            explanation = "'Desu' adalah penutup kalimat sopan (formal), sedangkan 'Da' adalah bentuk santai (informal).",
            exampleJp = "これはほんです。",
            exampleRomaji = "Kore wa hon desu.",
            exampleMeaning = "Ini adalah buku."
        )
    )

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedParticle = MutableStateFlow<GrammarParticle?>(null)
    val selectedParticle: StateFlow<GrammarParticle?> = _selectedParticle.asStateFlow()

    private val _aiQuestion = MutableStateFlow("")
    val aiQuestion: StateFlow<String> = _aiQuestion.asStateFlow()

    private val _aiAnswer = MutableStateFlow<String?>(null)
    val aiAnswer: StateFlow<String?> = _aiAnswer.asStateFlow()

    private val _isLoadingAi = MutableStateFlow(false)
    val isLoadingAi: StateFlow<Boolean> = _isLoadingAi.asStateFlow()

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectParticle(particle: GrammarParticle?) {
        _selectedParticle.value = if (_selectedParticle.value?.id == particle?.id) null else particle
    }

    fun updateAiQuestion(question: String) {
        _aiQuestion.value = question
    }

    fun askSensei(questionText: String? = null) {
        val q = (questionText ?: _aiQuestion.value).trim()
        if (q.isBlank() || _isLoadingAi.value) return

        _isLoadingAi.value = true
        _aiAnswer.value = null

        scope.launch {
            val contextParticle = _selectedParticle.value?.let {
                "Partikel terkait: ${it.character} (${it.romaji}) - ${it.title}"
            }
            val result = geminiRepo.explainGrammar(q, contextParticle)
            result.onSuccess { explanation ->
                _aiAnswer.value = explanation
                _isLoadingAi.value = false
            }.onFailure { err ->
                _aiAnswer.value = "⚠️ Gagal mendapatkan penjelasan: ${err.message ?: "Perlu koneksi internet"}"
                _isLoadingAi.value = false
            }
        }
    }

    fun clearAiAnswer() {
        _aiAnswer.value = null
    }

    fun goBack() {
        navController.goBack()
    }
}

package id.kotoba.app.model.repository

import id.kotoba.app.model.KanaItem
import id.kotoba.app.model.KanaType
import id.kotoba.app.model.Level
import id.kotoba.app.model.QuizQuestion

object KanaRepository {

    val level1Kana = listOf(
        KanaItem(
            id = "h_a",
            character = "あ",
            romaji = "a",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Arigatou (ありがとう) - Terima kasih!",
            meaning = "Bunyi vokal dasar 'A'",
            audioPrompt = "あ",
            mnemonic = "Bentuk seperti apel berputar"
        ),
        KanaItem(
            id = "h_i",
            character = "い",
            romaji = "i",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Ikimashou (行きましょう) - Ayo berangkat!",
            meaning = "Bunyi vokal dasar 'I'",
            audioPrompt = "い",
            mnemonic = "Dua garis sejajar melengkung"
        ),
        KanaItem(
            id = "h_u",
            character = "う",
            romaji = "u",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Umai (うまい) - Lezat / Hebat (Goku/Rengoku)!",
            meaning = "Bunyi vokal dasar 'U'",
            audioPrompt = "う",
            mnemonic = "Seperti orang membungkuk hormat"
        ),
        KanaItem(
            id = "h_e",
            character = "え",
            romaji = "e",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Ehh?! (ええっ) - Reaksi kaget karakter anime!",
            meaning = "Bunyi vokal dasar 'E'",
            audioPrompt = "え",
            mnemonic = "Bentuk seperti burung eksotis meluncur"
        ),
        KanaItem(
            id = "h_o",
            character = "お",
            romaji = "o",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Ohayou (おはよう) - Selamat pagi!",
            meaning = "Bunyi vokal dasar 'O'",
            audioPrompt = "お",
            mnemonic = "Huruf O yang melompat"
        ),
        KanaItem(
            id = "h_ka",
            character = "か",
            romaji = "ka",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Kawaii (かわいい) - Lucu / Imut!",
            meaning = "Konsonan K + vokal A",
            audioPrompt = "か",
            mnemonic = "Pedang samurai yang menebas ke bawah"
        ),
        KanaItem(
            id = "h_ki",
            character = "き",
            romaji = "ki",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Kimi (君) - Kamu (Kimi no Na wa)",
            meaning = "Konsonan K + vokal I",
            audioPrompt = "き",
            mnemonic = "Kunci bergigi dua"
        ),
        KanaItem(
            id = "h_ku",
            character = "く",
            romaji = "ku",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Kuro (黒) - Hitam (Kuroko / Kuroshitsuji)",
            meaning = "Konsonan K + vokal U",
            audioPrompt = "く",
            mnemonic = "Paruh burung cuckoo"
        ),
        KanaItem(
            id = "h_ke",
            character = "け",
            romaji = "ke",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Kekka (結果) - Hasil / Konsekuensi duel",
            meaning = "Konsonan K + vokal E",
            audioPrompt = "け",
            mnemonic = "Tong kayu / tong bir"
        ),
        KanaItem(
            id = "h_ko",
            character = "こ",
            romaji = "ko",
            type = KanaType.HIRAGANA_SEION,
            animeContext = "Konnichiwa (こんにちは) - Halo / Selamat siang",
            meaning = "Konsonan K + vokal O",
            audioPrompt = "こ",
            mnemonic = "Dua ikan koi berenang berdampingan"
        )
    )

    val level2Kana = listOf(
        KanaItem(
            id = "h_ga",
            character = "が",
            romaji = "ga",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Ganbare! (がんばれ) - Berjuanglah!",
            meaning = "Dakuon dari 'ka' dengan tenten (\")",
            audioPrompt = "が"
        ),
        KanaItem(
            id = "h_gi",
            character = "ぎ",
            romaji = "gi",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Gintama (銀魂) - Jiwa Perak",
            meaning = "Dakuon dari 'ki' dengan tenten (\")",
            audioPrompt = "ぎ"
        ),
        KanaItem(
            id = "h_za",
            character = "ざ",
            romaji = "za",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Zasshu (雑種) - Gilgamesh: 'Bastar* / Mongrel!'",
            meaning = "Dakuon dari 'sa' dengan tenten (\")",
            audioPrompt = "ざ"
        ),
        KanaItem(
            id = "h_da",
            character = "だ",
            romaji = "da",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Dame (だめ) - Tidak boleh / Terlarang!",
            meaning = "Dakuon dari 'ta' dengan tenten (\")",
            audioPrompt = "だ"
        ),
        KanaItem(
            id = "h_ba",
            character = "ば",
            romaji = "ba",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Baka! (ばか) - Tsundere: 'Dasar bodoh!'",
            meaning = "Dakuon dari 'ha' dengan tenten (\")",
            audioPrompt = "ば"
        ),
        KanaItem(
            id = "h_pa",
            character = "ぱ",
            romaji = "pa",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Pan (パン) - Roti / Makanan bekal sekolah",
            meaning = "Handakuon dari 'ha' dengan maru (○)",
            audioPrompt = "ぱ"
        )
    )

    val level3Kana = listOf(
        KanaItem(
            id = "h_kya",
            character = "きゃ",
            romaji = "kya",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Kyaaa! (きゃー) - Jeritan histeris anime rom-com!",
            meaning = "Kombinasi Ki + Ya kecil",
            audioPrompt = "きゃ"
        ),
        KanaItem(
            id = "h_ryu",
            character = "りゅ",
            romaji = "ryu",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Ryuu (竜 / 龍) - Naga (contoh: Rasengan Dragon)",
            meaning = "Kombinasi Ri + Yu kecil",
            audioPrompt = "りゅ"
        ),
        KanaItem(
            id = "h_sokuon",
            character = "っ",
            romaji = "double consonant (tsu kecil)",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Chotto matte! (ちょっと待って) - Tunggu sebentar!",
            meaning = "Sokuon jeda henti bunyi ganda",
            audioPrompt = "ちょっと待って"
        ),
        KanaItem(
            id = "h_sho",
            character = "しょ",
            romaji = "sho",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Shounen (少年) - Remaja / Genre aksi petualangan",
            meaning = "Kombinasi Shi + Yo kecil",
            audioPrompt = "しょ"
        )
    )

    val level4Vocab = listOf(
        KanaItem(
            id = "v_sugoi",
            character = "すごい",
            romaji = "sugoi",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Sugoi! - 'Luar biasa! Keren banget!'",
            meaning = "Kekaguman luar biasa saat pertarungan epik",
            audioPrompt = "すごい"
        ),
        KanaItem(
            id = "v_maji",
            character = "まじ",
            romaji = "maji",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Maji de?! (まじで) - 'Seriusan lu?!'",
            meaning = "Ungkapan slang tidak percaya",
            audioPrompt = "まじで"
        ),
        KanaItem(
            id = "v_yatta",
            character = "やった",
            romaji = "yatta",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Yatta!! - 'Horeee kita berhasil!'",
            meaning = "Pekikan kemenangan",
            audioPrompt = "やった"
        ),
        KanaItem(
            id = "v_senpai",
            character = "せんぱい",
            romaji = "senpai",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Notice me Senpai! - 'Kakak kelas / senior'",
            meaning = "Panggilan hormat untuk senior",
            audioPrompt = "せんぱい"
        ),
        KanaItem(
            id = "v_daisuki",
            character = "だいすき",
            romaji = "daisuki",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Daisuki dayo! - 'Aku sangat suka kamu!'",
            meaning = "Pernyataan cinta anime",
            audioPrompt = "だいすき"
        )
    )

    val level5Katakana = listOf(
        KanaItem(
            id = "k_anime",
            character = "アニメ",
            romaji = "anime",
            type = KanaType.KATAKANA,
            animeContext = "Anime (Animation) - Serial animasi Jepang!",
            meaning = "Katakana serapan bahasa Inggris",
            audioPrompt = "アニメ"
        ),
        KanaItem(
            id = "k_ramen",
            character = "ラーメン",
            romaji = "raamen",
            type = KanaType.KATAKANA,
            animeContext = "Ichiraku Ramen (Naruto) - Mie kuah khas Jepang!",
            meaning = "Menggunakan tanda vokal panjang (ー)",
            audioPrompt = "ラーメン"
        ),
        KanaItem(
            id = "k_meka",
            character = "メカ",
            romaji = "meka",
            type = KanaType.KATAKANA,
            animeContext = "Mecha (Gundam / Evangelion) - Robot tempur raksasa",
            meaning = "Katakana Me + Ka",
            audioPrompt = "メカ"
        ),
        KanaItem(
            id = "k_otaku",
            character = "オタク",
            romaji = "otaku",
            type = KanaType.KATAKANA,
            animeContext = "Otaku - Penggemar berat pop-culture Jepang!",
            meaning = "Katakana O + Ta + Ku",
            audioPrompt = "オタク"
        )
    )

    val level6Particles = listOf(
        KanaItem(
            id = "p_wa",
            character = "は (wa)",
            romaji = "wa",
            type = KanaType.PARTICLE,
            animeContext = "Watashi wa... (Luffy: 'Ore wa Kaizoku Ou ni naru!')",
            meaning = "Partikel penanda topik kalimat (ditulis 'ha', dibaca 'wa')",
            audioPrompt = "おれはかいぞくおうになる"
        ),
        KanaItem(
            id = "p_o",
            character = "を (o)",
            romaji = "o / wo",
            type = KanaType.PARTICLE,
            animeContext = "Ramen o taberu - 'Makan ramen'",
            meaning = "Partikel penanda objek langsung",
            audioPrompt = "ラーメンをたべる"
        ),
        KanaItem(
            id = "p_no",
            character = "の",
            romaji = "no",
            type = KanaType.PARTICLE,
            animeContext = "Kimi no Na wa - 'Namamu (Kepemilikan)'",
            meaning = "Partikel penghubung kepemilikan kata benda",
            audioPrompt = "きみのなわ"
        )
    )

    val level7Kanji = listOf(
        KanaItem(
            id = "k_ichi",
            character = "一",
            romaji = "ichi",
            type = KanaType.KANJI,
            animeContext = "Ichiban! (一番) - Nomor satu / Paling top!",
            meaning = "Angka satu (Garis mendatar tunggal)",
            audioPrompt = "いち",
            furigana = "いち",
            mnemonic = "Satu garis lurus sempurna"
        ),
        KanaItem(
            id = "k_ni",
            character = "二",
            romaji = "ni",
            type = KanaType.KANJI,
            animeContext = "Nijigen (二次元) - Dunia 2D (Anime/Manga)!",
            meaning = "Angka dua (Dua garis mendatar)",
            audioPrompt = "に",
            furigana = "に",
            mnemonic = "Dua garis sejajar horizontal"
        ),
        KanaItem(
            id = "k_san",
            character = "三",
            romaji = "san",
            type = KanaType.KANJI,
            animeContext = "Sanji (サンジ / 三時) - Nama koki One Piece!",
            meaning = "Angka tiga (Tiga garis horizontal)",
            audioPrompt = "さん",
            furigana = "さん",
            mnemonic = "Tiga garis bertumpuk rapi"
        ),
        KanaItem(
            id = "k_nichi",
            character = "日",
            romaji = "nichi / hi",
            type = KanaType.KANJI,
            animeContext = "Nihon (日本) - Negeri Matahari Terbit!",
            meaning = "Matahari / Hari",
            audioPrompt = "にち",
            furigana = "にち",
            mnemonic = "Bentuk jendela kotak menyinari hari"
        ),
        KanaItem(
            id = "k_tsuki",
            character = "月",
            romaji = "tsuki / getsu",
            type = KanaType.KANJI,
            animeContext = "Tsuki ga kirei desu ne - 'Bulannya indah ya (ungkapan cinta)'",
            meaning = "Bulan / Kalender bulan",
            audioPrompt = "つき",
            furigana = "つき",
            mnemonic = "Bulan sabit dengan dua awan kecil"
        )
    )

    val level8Kanji = listOf(
        KanaItem(
            id = "k_hito",
            character = "人",
            romaji = "hito / jin",
            type = KanaType.KANJI,
            animeContext = "Nihon-jin (日本人) - Orang Jepang!",
            meaning = "Orang / Manusia",
            audioPrompt = "ひと",
            furigana = "ひと",
            mnemonic = "Dua kaki orang sedang melangkah tegak"
        ),
        KanaItem(
            id = "k_dai",
            character = "大",
            romaji = "dai / ookii",
            type = KanaType.KANJI,
            animeContext = "Dai-suki! (大好き) - Sangat suka / Cinta besar!",
            meaning = "Besar / Agung",
            audioPrompt = "おおきい",
            furigana = "おおきい",
            mnemonic = "Orang melebarkan tangan dan kaki menandakan BESAR"
        ),
        KanaItem(
            id = "k_shou",
            character = "小",
            romaji = "shou / chiisai",
            type = KanaType.KANJI,
            animeContext = "Shounen (少年) - Remaja / Anak laki-laki",
            meaning = "Kecil",
            audioPrompt = "ちいさい",
            furigana = "ちいさい",
            mnemonic = "Satu tiang utama dengan dua titik kecil di sampingnya"
        ),
        KanaItem(
            id = "k_mizu",
            character = "水",
            romaji = "mizu / sui",
            type = KanaType.KANJI,
            animeContext = "Mizu no Kokyuu (Demon Slayer) - Pernafasan Air!",
            meaning = "Air murni yang mengalir",
            audioPrompt = "みず",
            furigana = "みず",
            mnemonic = "Aliran arus sungai memercik ke dua sisi"
        )
    )

    val level9Grammar = listOf(
        KanaItem(
            id = "g_tabemasu",
            character = "食べます",
            romaji = "tabemasu",
            type = KanaType.KANJI,
            animeContext = "Itadakimasu! Ramen o tabemasu!",
            meaning = "Makan (Bentuk sopan -masu)",
            audioPrompt = "たべます",
            furigana = "たべます",
            mnemonic = "Kata kerja golongan 2"
        ),
        KanaItem(
            id = "g_ikimasu",
            character = "行きます",
            romaji = "ikimasu",
            type = KanaType.KANJI,
            animeContext = "Ikimashou! - Ayo kita berangkat!",
            meaning = "Pergi (Bentuk sopan -masu)",
            audioPrompt = "いきます",
            furigana = "いきます",
            mnemonic = "Menuju ke suatu tempat"
        )
    )

    val level10Intro = listOf(
        KanaItem(
            id = "c_hajime",
            character = "初めまして",
            romaji = "hajimemashite",
            type = KanaType.CONVERSATION,
            animeContext = "Hajimemashite, Ore wa Monkey D. Luffy!",
            meaning = "Salam perkenalan pertama kali ('Senang bertemu')",
            audioPrompt = "はじめまして",
            furigana = "はじめまして"
        ),
        KanaItem(
            id = "c_yoroshiku",
            character = "よろしくお願いします",
            romaji = "yoroshiku onegaishimasu",
            type = KanaType.CONVERSATION,
            animeContext = "Yoroshiku ne! - Mohon bimbingan & kerjasamanya!",
            meaning = "Ungkapan sopan wajib dalam budaya Jepang",
            audioPrompt = "よろしくおねがいします",
            furigana = "よろしくおねがいします"
        )
    )

    val level11Situations = listOf(
        KanaItem(
            id = "c_sumimasen",
            character = "すみません",
            romaji = "sumimasen",
            type = KanaType.CONVERSATION,
            animeContext = "Sumimasen! - Memanggil pelayan di restoran anime!",
            meaning = "Permisi / Maaf",
            audioPrompt = "すみません",
            furigana = "すみません"
        ),
        KanaItem(
            id = "c_kore",
            character = "これをください",
            romaji = "kore o kudasai",
            type = KanaType.CONVERSATION,
            animeContext = "Kore o kudasai! - 'Tolong berikan yang ini!' di Konbini",
            meaning = "Ungkapan belanja praktis",
            audioPrompt = "これをください",
            furigana = "これをください"
        )
    )

    val level12Listening = listOf(
        KanaItem(
            id = "c_gambatte",
            character = "諦めないで！",
            romaji = "akiramenaide",
            type = KanaType.CONVERSATION,
            animeContext = "Zettai akiramenaide! - Jangan pernah menyerah!",
            meaning = "Kalimat motivasi pahlawan Shonen",
            audioPrompt = "あきらめないで",
            furigana = "あきらめないで"
        )
    )

    val allLevels = listOf(
        // Fase A: Fondasi Kana
        Level(
            id = "lvl_1",
            levelNumber = 1,
            title = "Hiragana Inti (Seion)",
            subtitle = "Vokal dasar & Baris Ka",
            description = "Kenali bentuk huruf vokal (a, i, u, e, o) dan baris ka dengan pelafalan asli.",
            category = "Fase A: Fondasi Kana",
            totalLessons = 10,
            xpReward = 100,
            isUnlocked = true,
            isCompleted = false,
            kanaList = level1Kana
        ),
        Level(
            id = "lvl_2",
            levelNumber = 2,
            title = "Hiragana Dakuon",
            subtitle = "Suara Ga, Za, Da, Ba, Pa",
            description = "Suara modifikasi dengan tenten (\") dan maru (○) yang sering terdengar di anime.",
            category = "Fase A: Fondasi Kana",
            totalLessons = 6,
            xpReward = 150,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level2Kana
        ),
        Level(
            id = "lvl_3",
            levelNumber = 3,
            title = "Youon & Sokuon",
            subtitle = "Bunyi Gabungan & Jeda Ganda",
            description = "Kombinasi bunyi kecil (Kya, Ryu, Sho) dan konsonan henti seperti 'Chotto'.",
            category = "Fase A: Fondasi Kana",
            totalLessons = 4,
            xpReward = 200,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level3Kana
        ),
        // Fase B: Kosakata & Katakana
        Level(
            id = "lvl_4",
            levelNumber = 4,
            title = "Kosakata Ikonik Anime",
            subtitle = "Sugoi, Maji, Yatta, Senpai",
            description = "Pelajari kosakata anime terpopuler tanpa beban kanji rumit di awal.",
            category = "Fase B: Kosakata & Katakana",
            totalLessons = 5,
            xpReward = 250,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level4Vocab
        ),
        Level(
            id = "lvl_5",
            levelNumber = 5,
            title = "Katakana Dasar",
            subtitle = "Aksara Serapan Anime & Pop Culture",
            description = "Bisa membaca istilah keren: Anime, Ramen, Mecha, dan Otaku dalam Katakana.",
            category = "Fase B: Kosakata & Katakana",
            totalLessons = 4,
            xpReward = 300,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level5Katakana
        ),
        // Fase C: Kanji Dasar & Tata Bahasa
        Level(
            id = "lvl_6",
            levelNumber = 6,
            title = "Partikel & Frase Sederhana",
            subtitle = "Menyusun Kalimat Pertama (Wa, O, No)",
            description = "Gunakan partikel Wa, O, dan No untuk memahami dialog anime sederhana.",
            category = "Fase C: Kanji & Tata Bahasa",
            totalLessons = 3,
            xpReward = 350,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level6Particles
        ),
        Level(
            id = "lvl_7",
            levelNumber = 7,
            title = "Kanji Angka & Waktu",
            subtitle = "Satu - Sepuluh, Hari, & Bulan",
            description = "Kuasai Kanji angka dan penanggalan Jepang dasar lewat mnemonic visual.",
            category = "Fase C: Kanji & Tata Bahasa",
            totalLessons = 5,
            xpReward = 400,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level7Kanji
        ),
        Level(
            id = "lvl_8",
            levelNumber = 8,
            title = "Kanji Kehidupan Sehari-hari",
            subtitle = "Orang, Alam, & Elemen (JLPT N5)",
            description = "Kanji esensial sehari-hari dengan bantuan Furigana aktif.",
            category = "Fase C: Kanji & Tata Bahasa",
            totalLessons = 4,
            xpReward = 450,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level8Kanji
        ),
        Level(
            id = "lvl_9",
            levelNumber = 9,
            title = "Pola Kalimat Dasar",
            subtitle = "Bentuk -masu & Kata Kerja Populer",
            description = "Pelajari cara menyusun kalimat positif, negatif, dan ajakan.",
            category = "Fase C: Kanji & Tata Bahasa",
            totalLessons = 2,
            xpReward = 500,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level9Grammar
        ),
        // Fase D: Percakapan
        Level(
            id = "lvl_10",
            levelNumber = 10,
            title = "Perkenalan Diri (Jikoshoukai)",
            subtitle = "Nama, Asal, & Hobi",
            description = "Berani memperkenalkan diri dalam suasana santai maupun formal.",
            category = "Fase D: Percakapan",
            totalLessons = 2,
            xpReward = 550,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level10Intro
        ),
        Level(
            id = "lvl_11",
            levelNumber = 11,
            title = "Situasi Sehari-hari",
            subtitle = "Di Konbini, Restoran, & Toko",
            description = "Frasa belanja dan memesan makanan tanpa rasa canggung.",
            category = "Fase D: Percakapan",
            totalLessons = 2,
            xpReward = 600,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level11Situations
        ),
        Level(
            id = "lvl_12",
            levelNumber = 12,
            title = "Listening Lanjutan & Mini-Cerita",
            subtitle = "Ujian Akhir Dialog Anime",
            description = "Tes pemahaman kutipan dialog anime seru tanpa teks bantuan.",
            category = "Fase D: Percakapan",
            totalLessons = 1,
            xpReward = 700,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level12Listening
        )
    )

    val quizBank: Map<String, List<QuizQuestion>> = mapOf(
        "lvl_1" to listOf(
            QuizQuestion(
                id = "q1_1",
                levelId = "lvl_1",
                questionText = "Huruf manakah yang dibaca 'a' (seperti dalam Arigatou)?",
                audioPrompt = "あ",
                visualCharacter = "あ",
                options = listOf("あ", "い", "う", "お"),
                correctOptionIndex = 0,
                explanation = "'あ' adalah huruf vokal pertama dalam Hiragana, berbunyi 'a'.",
                animeContext = "Arigatou gozaimasu!"
            ),
            QuizQuestion(
                id = "q1_2",
                levelId = "lvl_1",
                questionText = "Dengarkan audio! Huruf manakah yang sesuai dengan suara berikut?",
                audioPrompt = "か",
                visualCharacter = "?",
                options = listOf("き", "か", "く", "こ"),
                correctOptionIndex = 1,
                explanation = "Suara tersebut adalah 'KA' (か), seperti kata 'Kawaii'.",
                animeContext = "Kawaii ne!"
            ),
            QuizQuestion(
                id = "q1_3",
                levelId = "lvl_1",
                questionText = "Huruf 'う' dibaca sebagai apa?",
                audioPrompt = "う",
                visualCharacter = "う",
                options = listOf("O", "E", "U", "A"),
                correctOptionIndex = 2,
                explanation = "'う' dilafalkan 'U' (seperti pekikan 'Umai!' Rengoku).",
                animeContext = "Umai! Umai!"
            ),
            QuizQuestion(
                id = "q1_4",
                levelId = "lvl_1",
                questionText = "Manakah huruf 'ko' (seperti dalam Konnichiwa)?",
                audioPrompt = "こ",
                visualCharacter = "こ",
                options = listOf("け", "か", "き", "こ"),
                correctOptionIndex = 3,
                explanation = "'こ' dilafalkan 'KO', bentuknya seperti dua garis melengkung santai.",
                animeContext = "Konnichiwa!"
            )
        ),
        "lvl_2" to listOf(
            QuizQuestion(
                id = "q2_1",
                levelId = "lvl_2",
                questionText = "Huruf 'ば' berasal dari huruf 'は' dengan tanda tenten. Dibaca apa?",
                audioPrompt = "ば",
                visualCharacter = "ば",
                options = listOf("PA", "BA", "DA", "GA"),
                correctOptionIndex = 1,
                explanation = "Tanda tenten (\") mengubah suara H menjadi B ('BA', seperti kata 'Baka!').",
                animeContext = "Baka yarou!"
            ),
            QuizQuestion(
                id = "q2_2",
                levelId = "lvl_2",
                questionText = "Pilih huruf yang dibaca 'ga' (Ganbare!):",
                audioPrompt = "が",
                visualCharacter = "が",
                options = listOf("ざ", "だ", "が", "ば"),
                correctOptionIndex = 2,
                explanation = "'が' adalah dakuon dari 'か' yang berbunyi 'GA'.",
                animeContext = "Ganbare Deku!"
            ),
            QuizQuestion(
                id = "q2_3",
                levelId = "lvl_2",
                questionText = "Huruf 'ぱ' menggunakan tanda lingkaran (maru). Dibaca apa?",
                audioPrompt = "ぱ",
                visualCharacter = "ぱ",
                options = listOf("PA", "BA", "MA", "TA"),
                correctOptionIndex = 0,
                explanation = "Tanda lingkaran maru (○) mengubah bunyi menjadi P ('PA', seperti 'Pan' roti).",
                animeContext = "Yakisoba Pan!"
            )
        ),
        "lvl_3" to listOf(
            QuizQuestion(
                id = "q3_1",
                levelId = "lvl_3",
                questionText = "Huruf kecil 'っ' (sokuon) berfungsi untuk apa?",
                audioPrompt = "ちょっと待って",
                visualCharacter = "っ",
                options = listOf("Menghilangkan suara", "Jeda henti konsonan ganda (seperti chotto)", "Mengubah nada suara", "Tanda tanya"),
                correctOptionIndex = 1,
                explanation = "Sokuon 'っ' memberi jeda glotal / konsonan ganda seperti 'Chotto matte'.",
                animeContext = "Chotto matte kudasai!"
            ),
            QuizQuestion(
                id = "q3_2",
                levelId = "lvl_3",
                questionText = "Kombinasi huruf 'きゃ' dibaca...",
                audioPrompt = "きゃ",
                visualCharacter = "きゃ",
                options = listOf("Ki-ya", "Kya", "Kyo", "Kyu"),
                correctOptionIndex = 1,
                explanation = "Youon 'きゃ' dibaca luluh satu suku kata menjadi 'KYA'.",
                animeContext = "Kyaaa! Hazukashii!"
            )
        ),
        "lvl_4" to listOf(
            QuizQuestion(
                id = "q4_1",
                levelId = "lvl_4",
                questionText = "Kata anime 'すごい' (Sugoi) memiliki arti apa?",
                audioPrompt = "すごい",
                visualCharacter = "すごい",
                options = listOf("Luar biasa / Hebat", "Bodoh", "Terima kasih", "Sampai jumpa"),
                correctOptionIndex = 0,
                explanation = "'Sugoi' berarti luar biasa, hebat, atau menakjubkan!",
                animeContext = "Sugoi power!"
            ),
            QuizQuestion(
                id = "q4_2",
                levelId = "lvl_4",
                questionText = "Bagaimana tulisan Hiragana untuk ungkapan senior 'Senpai'?",
                audioPrompt = "せんぱい",
                visualCharacter = "せんぱい",
                options = listOf("せかい", "せんぱい", "すいへい", "さんぽ"),
                correctOptionIndex = 1,
                explanation = "'せんぱい' adalah ejaan Hiragana untuk 'senpai'.",
                animeContext = "Senpai ni aitai!"
            )
        ),
        "lvl_5" to listOf(
            QuizQuestion(
                id = "q5_1",
                levelId = "lvl_5",
                questionText = "Kata Katakana 'アニメ' dibaca...",
                audioPrompt = "アニメ",
                visualCharacter = "アニメ",
                options = listOf("Ame", "Anime", "Amerika", "Aniki"),
                correctOptionIndex = 1,
                explanation = "A-NI-ME (アニメ) adalah kata serapan untuk animasi Jepang.",
                animeContext = "Nihon no Anime!"
            ),
            QuizQuestion(
                id = "q5_2",
                levelId = "lvl_5",
                questionText = "Garis mendatar 'ー' pada kata Katakana 'ラーメン' menandakan apa?",
                audioPrompt = "ラーメン",
                visualCharacter = "ー",
                options = listOf("Suara berhenti mendadak", "Pemanjangan vokal (Raa-men)", "Tanda baca titik", "Huruf mati"),
                correctOptionIndex = 1,
                explanation = "Garis 'ー' (chōonpu) melambangkan bunyi vokal panjang dalam Katakana.",
                animeContext = "Ichiraku Ramen!"
            )
        ),
        "lvl_6" to listOf(
            QuizQuestion(
                id = "q6_1",
                levelId = "lvl_6",
                questionText = "Partikel penanda topik kalimat yang ditulis 'は' dibaca sebagai...",
                audioPrompt = "おれはかいぞくおうになる",
                visualCharacter = "は",
                options = listOf("Ha", "Wa", "O", "Ka"),
                correctOptionIndex = 1,
                explanation = "Sebagai partikel gramatikal, huruf 'は' selalu dilafalkan 'WA'.",
                animeContext = "Ore WA Kaizoku Ou ni naru!"
            ),
            QuizQuestion(
                id = "q6_2",
                levelId = "lvl_6",
                questionText = "Partikel 'の' (no) seperti pada 'Kimi no Na wa' berfungsi untuk apa?",
                audioPrompt = "きみのなわ",
                visualCharacter = "の",
                options = listOf("Menunjukkan kepemilikan / asosiasi", "Menandakan tujuan tempat", "Menyatakan harga", "Kata seru kaget"),
                correctOptionIndex = 0,
                explanation = "'Kimi no Na wa' berarti 'Namamu' (Nama milik kamu).",
                animeContext = "Kimi no Na wa (Your Name)"
            )
        ),
        "lvl_7" to listOf(
            QuizQuestion(
                id = "q7_1",
                levelId = "lvl_7",
                questionText = "Kanji '一' melambangkan angka berapa?",
                audioPrompt = "いち",
                visualCharacter = "一",
                options = listOf("Satu (1)", "Dua (2)", "Tiga (3)", "Sepuluh (10)"),
                correctOptionIndex = 0,
                explanation = "Garis horizontal tunggal '一' dibaca 'ICHI' (Satu).",
                animeContext = "Ichiban!"
            ),
            QuizQuestion(
                id = "q7_2",
                levelId = "lvl_7",
                questionText = "Kanji '日' (nichi / hi) memiliki arti...",
                audioPrompt = "にち",
                visualCharacter = "日",
                options = listOf("Matahari / Hari", "Bulan", "Air", "Pohon"),
                correctOptionIndex = 0,
                explanation = "'日' melambangkan matahari dan hari, seperti pada 'Nihon' (Jepang).",
                animeContext = "Nihon (日本)"
            )
        ),
        "lvl_8" to listOf(
            QuizQuestion(
                id = "q8_1",
                levelId = "lvl_8",
                questionText = "Kanji '人' melambangkan apa?",
                audioPrompt = "ひと",
                visualCharacter = "人",
                options = listOf("Orang / Manusia", "Gunung", "Pintu", "Padi"),
                correctOptionIndex = 0,
                explanation = "'人' dibaca 'hito' atau 'jin', artinya orang (contoh: Anime-jin, Nihon-jin).",
                animeContext = "Nihon-jin (Orang Jepang)"
            )
        ),
        "lvl_9" to listOf(
            QuizQuestion(
                id = "q9_1",
                levelId = "lvl_9",
                questionText = "Bentuk sopan 'makan' dalam bahasa Jepang adalah...",
                audioPrompt = "たべます",
                visualCharacter = "食べます",
                options = listOf("Tabemasu", "Nomimasu", "Ikimasu", "Mimasu"),
                correctOptionIndex = 0,
                explanation = "Kata kerja 'makan' bentuk -masu adalah 'Tabemasu'.",
                animeContext = "Ramen o tabemasu!"
            )
        ),
        "lvl_10" to listOf(
            QuizQuestion(
                id = "q10_1",
                levelId = "lvl_10",
                questionText = "Ungkapan pembuka saat pertama kali berkenalan adalah...",
                audioPrompt = "はじめまして",
                visualCharacter = "初めまして",
                options = listOf("Hajimemashite", "Sayounara", "Oyasumi", "Arigatou"),
                correctOptionIndex = 0,
                explanation = "'Hajimemashite' diucapkan saat pertama kali bertemu seseorang.",
                animeContext = "Hajimemashite! Ore wa Luffy!"
            )
        ),
        "lvl_11" to listOf(
            QuizQuestion(
                id = "q11_1",
                levelId = "lvl_11",
                questionText = "Bagaimana cara sopan memanggil staf atau meminta maaf di Jepang?",
                audioPrompt = "すみません",
                visualCharacter = "すみません",
                options = listOf("Sumimasen", "Gomen", "Baka", "Matte"),
                correctOptionIndex = 0,
                explanation = "'Sumimasen' adalah kata serbaguna untuk 'Permisi' atau 'Maaf'.",
                animeContext = "Sumimasen! Kore o kudasai!"
            )
        ),
        "lvl_12" to listOf(
            QuizQuestion(
                id = "q12_1",
                levelId = "lvl_12",
                questionText = "Ungkapan '諦めないで！' (Akiramenaide) berarti...",
                audioPrompt = "あきらめないで",
                visualCharacter = "諦めないで",
                options = listOf("Jangan menyerah!", "Cepat lari!", "Makanlah ini!", "Selamat tinggal!"),
                correctOptionIndex = 0,
                explanation = "'Akiramenaide!' adalah seruan khas karakter utama: 'Jangan menyerah!'",
                animeContext = "Zettai akiramenaide!"
            )
        )
    )
}

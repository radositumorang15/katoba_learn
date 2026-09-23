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
            audioPrompt = "が",
            mnemonic = "か (ka) ditambah dua garis kecil jadi suara berat GA"
        ),
        KanaItem(
            id = "h_gi",
            character = "ぎ",
            romaji = "gi",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Gintama (銀魂) - Jiwa Perak",
            meaning = "Dakuon dari 'ki' dengan tenten (\")",
            audioPrompt = "ぎ",
            mnemonic = "き (ki) bergetar menjadi GI seperti gitar"
        ),
        KanaItem(
            id = "h_za",
            character = "ざ",
            romaji = "za",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Zasshu (雑種) - Gilgamesh: 'Bastar* / Mongrel!'",
            meaning = "Dakuon dari 'sa' dengan tenten (\")",
            audioPrompt = "ざ",
            mnemonic = "さ (sa) mendengung menjadi ZA seperti lebah"
        ),
        KanaItem(
            id = "h_da",
            character = "だ",
            romaji = "da",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Dame (だめ) - Tidak boleh / Terlarang!",
            meaning = "Dakuon dari 'ta' dengan tenten (\")",
            audioPrompt = "だ",
            mnemonic = "た (ta) dipukul jadi DA seperti drum"
        ),
        KanaItem(
            id = "h_ba",
            character = "ば",
            romaji = "ba",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Baka! (ばか) - Tsundere: 'Dasar bodoh!'",
            meaning = "Dakuon dari 'ha' dengan tenten (\")",
            audioPrompt = "ば",
            mnemonic = "は (ha) meledak jadi BA seperti bom"
        ),
        KanaItem(
            id = "h_pa",
            character = "ぱ",
            romaji = "pa",
            type = KanaType.HIRAGANA_DAKUON,
            animeContext = "Pan (パン) - Roti / Makanan bekal sekolah",
            meaning = "Handakuon dari 'ha' dengan maru (○)",
            audioPrompt = "ぱ",
            mnemonic = "は (ha) dengan gelembung bulat jadi PA ringan"
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
            audioPrompt = "きゃ",
            mnemonic = "Ki kecilkan Ya: suara jeritan cewek anime"
        ),
        KanaItem(
            id = "h_ryu",
            character = "りゅ",
            romaji = "ryu",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Ryuu (竜 / 龍) - Naga (contoh: Rasengan Dragon)",
            meaning = "Kombinasi Ri + Yu kecil",
            audioPrompt = "りゅ",
            mnemonic = "Ri + Yu kecil meluncur seperti naga terbang"
        ),
        KanaItem(
            id = "h_sokuon",
            character = "っ",
            romaji = "double consonant (tsu kecil)",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Chotto matte! (ちょっと待って) - Tunggu sebentar!",
            meaning = "Sokuon jeda henti bunyi ganda",
            audioPrompt = "ちょっと待って",
            mnemonic = "Tsu kecil = rem mendadak, hentikan napas sejenak!"
        ),
        KanaItem(
            id = "h_sho",
            character = "しょ",
            romaji = "sho",
            type = KanaType.YOUON_SOKUON,
            animeContext = "Shounen (少年) - Remaja / Genre aksi petualangan",
            meaning = "Kombinasi Shi + Yo kecil",
            audioPrompt = "しょ",
            mnemonic = "Shi + Yo kecil melebur jadi SHO pendek"
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
            audioPrompt = "すごい",
            mnemonic = "Su-Go-I! Tiga suku kata penuh energi kagum"
        ),
        KanaItem(
            id = "v_maji",
            character = "まじ",
            romaji = "maji",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Maji de?! (まじで) - 'Seriusan lu?!'",
            meaning = "Ungkapan slang tidak percaya",
            audioPrompt = "まじで",
            mnemonic = "Ma-Ji = Magic? Seserius itu?!"
        ),
        KanaItem(
            id = "v_yatta",
            character = "やった",
            romaji = "yatta",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Yatta!! - 'Horeee kita berhasil!'",
            meaning = "Pekikan kemenangan",
            audioPrompt = "やった",
            mnemonic = "Ya-TTA! Teriakan gembira dengan pukulan udara"
        ),
        KanaItem(
            id = "v_senpai",
            character = "せんぱい",
            romaji = "senpai",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Notice me Senpai! - 'Kakak kelas / senior'",
            meaning = "Panggilan hormat untuk senior",
            audioPrompt = "せんぱい",
            mnemonic = "Sen(ior) + pai(ring) = kakak senior idaman"
        ),
        KanaItem(
            id = "v_daisuki",
            character = "だいすき",
            romaji = "daisuki",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Daisuki dayo! - 'Aku sangat suka kamu!'",
            meaning = "Pernyataan cinta anime",
            audioPrompt = "だいすき",
            mnemonic = "Dai(besar) + Suki(suka) = Cinta BESAR"
        ),
        KanaItem(
            id = "v_naruhodo",
            character = "なるほど",
            romaji = "naruhodo",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Naruhodo! - 'Aaah begitu ya! Paham!'",
            meaning = "Ungkapan 'Aha!' saat memahami sesuatu",
            audioPrompt = "なるほど",
            mnemonic = "Naru(jadi) + hodo(sejauh) = Oh jadi begitu!"
        ),
        KanaItem(
            id = "v_kakkoii",
            character = "かっこいい",
            romaji = "kakkoii",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Kakkoii! - 'Keren banget!' (memuji cowok keren)",
            meaning = "Keren / Ganteng / Tampan",
            audioPrompt = "かっこいい",
            mnemonic = "Kakko(penampilan) + ii(bagus) = tampilan keren!"
        ),
        KanaItem(
            id = "v_yabai",
            character = "やばい",
            romaji = "yabai",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Yabai! - 'Gila! Bahaya!' (slang modern Jepang)",
            meaning = "Gila / Luar biasa / Bahaya (bisa positif & negatif)",
            audioPrompt = "やばい",
            mnemonic = "Ya-bai! Bisa WOW atau AWAS tergantung konteks"
        ),
        KanaItem(
            id = "v_urusai",
            character = "うるさい",
            romaji = "urusai",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Urusai! - 'Berisik!' (tsundere klasik)",
            meaning = "Berisik / Ribut (ekspresi kesal khas tsundere)",
            audioPrompt = "うるさい",
            mnemonic = "U-ru-sai! Tiga suku kata penuh kekesalan"
        ),
        KanaItem(
            id = "v_nakama",
            character = "なかま",
            romaji = "nakama",
            type = KanaType.ANIME_VOCAB,
            animeContext = "Nakama da! - 'Kita teman sejati!' (Luffy, One Piece)",
            meaning = "Teman / Sahabat / Rekan seperjuangan",
            audioPrompt = "なかま",
            mnemonic = "Na-ka-ma: ikatan persahabatan One Piece"
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
            audioPrompt = "アニメ",
            mnemonic = "A-NI-ME: tiga karakter tajam khas Katakana"
        ),
        KanaItem(
            id = "k_ramen",
            character = "ラーメン",
            romaji = "raamen",
            type = KanaType.KATAKANA,
            animeContext = "Ichiraku Ramen (Naruto) - Mie kuah khas Jepang!",
            meaning = "Menggunakan tanda vokal panjang (ー)",
            audioPrompt = "ラーメン",
            mnemonic = "RA-A-MEN: garis panjang ー = tarik vokal"
        ),
        KanaItem(
            id = "k_meka",
            character = "メカ",
            romaji = "meka",
            type = KanaType.KATAKANA,
            animeContext = "Mecha (Gundam / Evangelion) - Robot tempur raksasa",
            meaning = "Katakana Me + Ka",
            audioPrompt = "メカ",
            mnemonic = "ME-KA: dua huruf tajam seperti besi robot"
        ),
        KanaItem(
            id = "k_otaku",
            character = "オタク",
            romaji = "otaku",
            type = KanaType.KATAKANA,
            animeContext = "Otaku - Penggemar berat pop-culture Jepang!",
            meaning = "Katakana O + Ta + Ku",
            audioPrompt = "オタク",
            mnemonic = "O-TA-KU: tiga suku kata identitas wibu"
        ),
        KanaItem(
            id = "k_geemu",
            character = "ゲーム",
            romaji = "geemu",
            type = KanaType.KATAKANA,
            animeContext = "Game (SAO / No Game No Life) - Dunia permainan!",
            meaning = "Permainan / Video game (serapan Inggris)",
            audioPrompt = "ゲーム",
            mnemonic = "GE-E-MU: vokal panjang ー pada E"
        ),
        KanaItem(
            id = "k_terebi",
            character = "テレビ",
            romaji = "terebi",
            type = KanaType.KATAKANA,
            animeContext = "Terebi de anime o mimasu! - Nonton anime di TV!",
            meaning = "Televisi (dari 'Television')",
            audioPrompt = "テレビ",
            mnemonic = "TE-RE-BI: singkatan khas Jepang dari Television"
        ),
        KanaItem(
            id = "k_konbini",
            character = "コンビニ",
            romaji = "konbini",
            type = KanaType.KATAKANA,
            animeContext = "Konbini (7-Eleven) - Minimarket serba ada di anime!",
            meaning = "Minimarket / Convenience store",
            audioPrompt = "コンビニ",
            mnemonic = "KON-BI-NI: singkatan dari Convenience"
        ),
        KanaItem(
            id = "k_manga",
            character = "マンガ",
            romaji = "manga",
            type = KanaType.KATAKANA,
            animeContext = "Manga o yomimasu! - Baca komik Jepang!",
            meaning = "Komik Jepang (ditulis Katakana untuk efek modern)",
            audioPrompt = "マンガ",
            mnemonic = "MA-N-GA: tiga huruf sumber budaya pop"
        ),
        KanaItem(
            id = "k_karaoke",
            character = "カラオケ",
            romaji = "karaoke",
            type = KanaType.KATAKANA,
            animeContext = "Karaoke ni ikimashou! - Ayo ke karaoke!",
            meaning = "Bernyanyi tanpa band (kara=kosong + oke=orkestra)",
            audioPrompt = "カラオケ",
            mnemonic = "KA-RA-O-KE: orkestra kosong = nyanyi sendiri!"
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
            audioPrompt = "おれはかいぞくおうになる",
            mnemonic = "Ditulis は tapi dibaca WA jika jadi partikel"
        ),
        KanaItem(
            id = "p_o",
            character = "を (o)",
            romaji = "o / wo",
            type = KanaType.PARTICLE,
            animeContext = "Ramen o taberu - 'Makan ramen'",
            meaning = "Partikel penanda objek langsung",
            audioPrompt = "ラーメンをたべる",
            mnemonic = "を menunjuk BENDA yang kena aksi kata kerja"
        ),
        KanaItem(
            id = "p_no",
            character = "の",
            romaji = "no",
            type = KanaType.PARTICLE,
            animeContext = "Kimi no Na wa - 'Namamu (Kepemilikan)'",
            meaning = "Partikel penghubung kepemilikan kata benda",
            audioPrompt = "きみのなわ",
            mnemonic = "の = 'punya': Kimi NO Na = Nama MILIK kamu"
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
        ),
        KanaItem(
            id = "g_nomimasu",
            character = "飲みます",
            romaji = "nomimasu",
            type = KanaType.KANJI,
            animeContext = "Ocha o nomimasu - Minum teh hijau!",
            meaning = "Minum (Bentuk sopan -masu)",
            audioPrompt = "のみます",
            furigana = "のみます",
            mnemonic = "Nomi = minum, -masu = sopan"
        ),
        KanaItem(
            id = "g_mimasu",
            character = "見ます",
            romaji = "mimasu",
            type = KanaType.KANJI,
            animeContext = "Anime o mimasu! - Nonton anime!",
            meaning = "Melihat / Menonton (Bentuk sopan -masu)",
            audioPrompt = "みます",
            furigana = "みます",
            mnemonic = "Mi = mata melihat, -masu = sopan"
        ),
        KanaItem(
            id = "g_kakimasu",
            character = "書きます",
            romaji = "kakimasu",
            type = KanaType.KANJI,
            animeContext = "Tegami o kakimasu - Menulis surat!",
            meaning = "Menulis (Bentuk sopan -masu)",
            audioPrompt = "かきます",
            furigana = "かきます",
            mnemonic = "Kaki = menulis dengan tangan"
        ),
        KanaItem(
            id = "g_yomimasu",
            character = "読みます",
            romaji = "yomimasu",
            type = KanaType.KANJI,
            animeContext = "Manga o yomimasu! - Baca manga!",
            meaning = "Membaca (Bentuk sopan -masu)",
            audioPrompt = "よみます",
            furigana = "よみます",
            mnemonic = "Yomi = membaca, mata menyapu huruf"
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
            furigana = "はじめまして",
            mnemonic = "Hajime(mulai) + mashite = 'Ini awal pertemuan kita'"
        ),
        KanaItem(
            id = "c_yoroshiku",
            character = "よろしくお願いします",
            romaji = "yoroshiku onegaishimasu",
            type = KanaType.CONVERSATION,
            animeContext = "Yoroshiku ne! - Mohon bimbingan & kerjasamanya!",
            meaning = "Ungkapan sopan wajib dalam budaya Jepang",
            audioPrompt = "よろしくおねがいします",
            furigana = "よろしくおねがいします",
            mnemonic = "Yoroshiku = tolong baik-baik ya sama saya"
        ),
        KanaItem(
            id = "c_namae",
            character = "わたしのなまえは...",
            romaji = "watashi no namae wa...",
            type = KanaType.CONVERSATION,
            animeContext = "Watashi no namae wa Sakura desu!",
            meaning = "Nama saya adalah... (pola perkenalan dasar)",
            audioPrompt = "わたしのなまえは",
            furigana = "わたしのなまえは",
            mnemonic = "Watashi(saya) + no(punya) + namae(nama) + wa(topik)"
        ),
        KanaItem(
            id = "c_kara",
            character = "...からきました",
            romaji = "...kara kimashita",
            type = KanaType.CONVERSATION,
            animeContext = "Indonesia kara kimashita! - Saya dari Indonesia!",
            meaning = "Berasal dari... (menyatakan asal)",
            audioPrompt = "インドネシアからきました",
            furigana = "からきました",
            mnemonic = "Kara(dari) + kimashita(datang) = datang dari..."
        ),
        KanaItem(
            id = "c_shumi",
            character = "しゅみは...です",
            romaji = "shumi wa ... desu",
            type = KanaType.CONVERSATION,
            animeContext = "Shumi wa anime desu! - Hobi saya anime!",
            meaning = "Hobi saya adalah... (menyatakan kegemaran)",
            audioPrompt = "しゅみはアニメです",
            furigana = "しゅみは…です",
            mnemonic = "Shumi(hobi) + wa(topik) + desu(adalah)"
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
            furigana = "すみません",
            mnemonic = "Su-mi-ma-sen: empat suku kata untuk sopan santun"
        ),
        KanaItem(
            id = "c_kore",
            character = "これをください",
            romaji = "kore o kudasai",
            type = KanaType.CONVERSATION,
            animeContext = "Kore o kudasai! - 'Tolong berikan yang ini!' di Konbini",
            meaning = "Ungkapan belanja praktis",
            audioPrompt = "これをください",
            furigana = "これをください",
            mnemonic = "Kore(ini) + o(objek) + kudasai(tolong beri)"
        ),
        KanaItem(
            id = "c_ikura",
            character = "いくらですか",
            romaji = "ikura desu ka",
            type = KanaType.CONVERSATION,
            animeContext = "Ikura desu ka? - Berapa harganya di toko Akihabara!",
            meaning = "Berapa harganya? (belanja praktis)",
            audioPrompt = "いくらですか",
            furigana = "いくらですか",
            mnemonic = "Ikura(berapa) + desu ka(?) = tanya harga"
        ),
        KanaItem(
            id = "c_osusume",
            character = "おすすめは？",
            romaji = "osusume wa?",
            type = KanaType.CONVERSATION,
            animeContext = "Osusume wa? - Minta rekomendasi menu di izakaya!",
            meaning = "Apa rekomendasinya? (di restoran/toko)",
            audioPrompt = "おすすめは",
            furigana = "おすすめは",
            mnemonic = "O-susume = rekomendasi, tinggal tanya!"
        ),
        KanaItem(
            id = "c_oishii",
            character = "おいしいです",
            romaji = "oishii desu",
            type = KanaType.CONVERSATION,
            animeContext = "Oishii! - Reaksi makan di anime Food Wars!",
            meaning = "Enak! / Lezat! (memuji makanan)",
            audioPrompt = "おいしいです",
            furigana = "おいしいです",
            mnemonic = "Oi-shii! = suara keenakan saat makan"
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
            furigana = "あきらめないで",
            mnemonic = "Akirame(menyerah) + nai(jangan) + de(!) = JANGAN MENYERAH"
        ),
        KanaItem(
            id = "c_madamada",
            character = "まだまだだね",
            romaji = "mada mada da ne",
            type = KanaType.CONVERSATION,
            animeContext = "Mada mada da ne! - Ryoma (Prince of Tennis): 'Masih jauh!'",
            meaning = "Masih jauh / Belum cukup (tantangan rival)",
            audioPrompt = "まだまだだね",
            furigana = "まだまだだね",
            mnemonic = "Mada(belum) x2 = masih jauuuuh banget"
        ),
        KanaItem(
            id = "c_orewa",
            character = "俺の名前は...",
            romaji = "ore no namae wa...",
            type = KanaType.CONVERSATION,
            animeContext = "Ore no namae wa Uzumaki Naruto dattebayo!",
            meaning = "Namaku adalah... (gaya maskulin / protagonis)",
            audioPrompt = "おれのなまえは",
            furigana = "おれのなまえは",
            mnemonic = "Ore(aku-cowok) + no namae wa = gaya perkenalan hero"
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
            totalLessons = 10,
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
            totalLessons = 9,
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
            totalLessons = 6,
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
            totalLessons = 5,
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
            totalLessons = 5,
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
            totalLessons = 3,
            xpReward = 700,
            isUnlocked = false,
            isCompleted = false,
            kanaList = level12Listening
        )
    )

    val quizBank: MutableMap<String, List<QuizQuestion>> = mutableMapOf(
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
            ),
            QuizQuestion(
                id = "q3_3",
                levelId = "lvl_3",
                questionText = "Kombinasi huruf 'りゅ' (Ryu) melambangkan kata apa dalam anime?",
                audioPrompt = "りゅ",
                visualCharacter = "りゅ",
                options = listOf("Naga (Dragon)", "Kelinci", "Ikan", "Kucing"),
                correctOptionIndex = 0,
                explanation = "'りゅ' (Ryu/Ryuu) berarti Naga, sering muncul dalam jurus dan nama pahlawan anime.",
                animeContext = "Ryuu no Kokyuu!"
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
            ),
            QuizQuestion(
                id = "q4_3",
                levelId = "lvl_4",
                questionText = "Kata anime 'なるほど' (Naruhodo) diucapkan saat...",
                audioPrompt = "なるほど",
                visualCharacter = "なるほど",
                options = listOf("Memahami penjelasan ('Aaah begitu ya!')", "Marah besar", "Meminta maaf", "Menolak ajakan"),
                correctOptionIndex = 0,
                explanation = "'なるほど' (Naruhodo) adalah seruan saat kita memahami maksud perkataan orang lain.",
                animeContext = "Naruhodo ne!"
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
            ),
            QuizQuestion(
                id = "q5_3",
                levelId = "lvl_5",
                questionText = "Kata Katakana 'コンビニ' (Konbini) adalah sebutan untuk...",
                audioPrompt = "コンビニ",
                visualCharacter = "コンビニ",
                options = listOf("Minimarket (Convenience Store)", "Komputer", "Konser musik", "Pendingin ruangan"),
                correctOptionIndex = 0,
                explanation = "'コンビニ' adalah singkatan khas Jepang untuk Convenience Store serba ada.",
                animeContext = "Konbini de kaimono!"
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
            ),
            QuizQuestion(
                id = "q6_3",
                levelId = "lvl_6",
                questionText = "Partikel yang tepat untuk kalimat: 'Ramen __ tabemasu' (Makan ramen) adalah...",
                audioPrompt = "ラーメンをたべる",
                visualCharacter = "を",
                options = listOf("を (o)", "は (wa)", "の (no)", "か (ka)"),
                correctOptionIndex = 0,
                explanation = "Partikel 'を' (dibaca 'o') menandai objek langsung yang dikenai perbuatan kata kerja.",
                animeContext = "Ramen o tabemasu!"
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
            ),
            QuizQuestion(
                id = "q7_3",
                levelId = "lvl_7",
                questionText = "Kanji '月' (tsuki / getsu) melambangkan apa?",
                audioPrompt = "つき",
                visualCharacter = "月",
                options = listOf("Bulan", "Matahari", "Bintang", "Awan"),
                correctOptionIndex = 0,
                explanation = "Kanji '月' melambangkan bulan (satelit/kalender), seperti dalam ungkapan romantis anime 'Tsuki ga kirei'.",
                animeContext = "Tsuki ga kirei desu ne"
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
            ),
            QuizQuestion(
                id = "q8_2",
                levelId = "lvl_8",
                questionText = "Kanji '水' (mizu) yang sering ada pada jurus anime melambangkan elemen apa?",
                audioPrompt = "みず",
                visualCharacter = "水",
                options = listOf("Air", "Api", "Bumi", "Petir"),
                correctOptionIndex = 0,
                explanation = "'水' dibaca 'mizu' (air), seperti pada pernapasan air Demon Slayer.",
                animeContext = "Mizu no Kokyuu!"
            ),
            QuizQuestion(
                id = "q8_3",
                levelId = "lvl_8",
                questionText = "Kanji '大' (dai / ookii) memiliki arti lawan dari '小' (chiisai). Apa artinya?",
                audioPrompt = "おおきい",
                visualCharacter = "大",
                options = listOf("Besar", "Kecil", "Cepat", "Jauh"),
                correctOptionIndex = 0,
                explanation = "'大' melambangkan manusia merentangkan tangan lebar yang berarti BESAR.",
                animeContext = "Daisuki (大好き)!"
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
            ),
            QuizQuestion(
                id = "q9_2",
                levelId = "lvl_9",
                questionText = "Bentuk sopan -masu untuk kata kerja 'minum' adalah...",
                audioPrompt = "のみます",
                visualCharacter = "飲みます",
                options = listOf("Nomimasu", "Tabemasu", "Mimasu", "Kakimasu"),
                correctOptionIndex = 0,
                explanation = "'飲みます' (Nomimasu) berarti minum, seperti 'Ocha o nomimasu'.",
                animeContext = "Ocha o nomimasu!"
            ),
            QuizQuestion(
                id = "q9_3",
                levelId = "lvl_9",
                questionText = "Kalimat 'Anime o mimasu' (アニメを見ます) memiliki arti apa?",
                audioPrompt = "みます",
                visualCharacter = "見ます",
                options = listOf("Menonton anime", "Menggambar anime", "Membeli anime", "Mendengarkan anime"),
                correctOptionIndex = 0,
                explanation = "'見ます' (Mimasu) berarti melihat atau menonton.",
                animeContext = "Anime o mimasu!"
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
            ),
            QuizQuestion(
                id = "q10_2",
                levelId = "lvl_10",
                questionText = "Frasa 'よろしくお願いします' (Yoroshiku onegaishimasu) bermakna...",
                audioPrompt = "よろしくおねがいします",
                visualCharacter = "よろしくお願いします",
                options = listOf("Mohon bimbingan / bantuannya", "Selamat tinggal", "Permisi mau lewat", "Berapa harganya?"),
                correctOptionIndex = 0,
                explanation = "'Yoroshiku onegaishimasu' memohon hubungan baik, kerja sama, dan bimbingan.",
                animeContext = "Yoroshiku ne!"
            ),
            QuizQuestion(
                id = "q10_3",
                levelId = "lvl_10",
                questionText = "Pola kalimat '... kara kimashita' digunakan untuk menyatakan apa?",
                audioPrompt = "インドネシアからきました",
                visualCharacter = "...からきました",
                options = listOf("Asal negara / daerah", "Makanan kesukaan", "Waktu sekarang", "Pekerjaan"),
                correctOptionIndex = 0,
                explanation = "'... kara kimashita' berarti 'datang/berasal dari...', untuk menyatakan asal daerah.",
                animeContext = "Indonesia kara kimashita!"
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
            ),
            QuizQuestion(
                id = "q11_2",
                levelId = "lvl_11",
                questionText = "Di toko/restoran, pertanyaan 'いくらですか' (Ikura desu ka?) digunakan untuk menanyakan apa?",
                audioPrompt = "いくらですか",
                visualCharacter = "いくらですか",
                options = listOf("Berapa harganya?", "Berapa jam lagi?", "Siapa namanya?", "Di mana toiletnya?"),
                correctOptionIndex = 0,
                explanation = "'Ikura desu ka?' berarti 'Berapa harganya?' saat berbelanja.",
                animeContext = "Kore wa ikura desu ka?"
            ),
            QuizQuestion(
                id = "q11_3",
                levelId = "lvl_11",
                questionText = "Ekspresi karakter anime saat menikmati makanan lezat adalah...",
                audioPrompt = "おいしいです",
                visualCharacter = "おいしいです",
                options = listOf("Oishii desu! (Enak!)", "Itai desu! (Sakit!)", "Abunai desu! (Bahaya!)", "Nemui desu! (Ngantuk!)"),
                correctOptionIndex = 0,
                explanation = "'おいしいです' (Oishii desu) berarti 'Enak/Lezat!' memuji masakan.",
                animeContext = "Umai! Oishii!"
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
            ),
            QuizQuestion(
                id = "q12_2",
                levelId = "lvl_12",
                questionText = "Kutipan rival terkenal 'まだまだだね' (Mada mada da ne) memiliki arti...",
                audioPrompt = "まだまだだね",
                visualCharacter = "まだまだだね",
                options = listOf("Masih belum cukup / Masih jauh!", "Sudah hebat!", "Kamu menang!", "Bagus sekali!"),
                correctOptionIndex = 0,
                explanation = "'Mada mada da ne' adalah catchphrase Echizen Ryoma: 'Kemampuanmu masih jauh!'.",
                animeContext = "Mada mada da ne!"
            ),
            QuizQuestion(
                id = "q12_3",
                levelId = "lvl_12",
                questionText = "Kata ganti orang pertama (aku) yang khas dipakai hero anime cowok adalah...",
                audioPrompt = "おれのなまえは",
                visualCharacter = "俺 (Ore)",
                options = listOf("Ore (俺)", "Watashi (私)", "Anata (あなた)", "Kare (彼)"),
                correctOptionIndex = 0,
                explanation = "'俺' (Ore) adalah sebutan 'aku' gaya maskulin yang sangat khas bagi jagoan anime Shonen.",
                animeContext = "Ore wa Kaizoku Ou ni naru!"
            )
        )
    )

    fun setQuestionsForLevel(levelId: String, questions: List<QuizQuestion>) {
        if (questions.isNotEmpty()) {
            quizBank[levelId] = questions
        }
    }
}

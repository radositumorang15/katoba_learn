# Product Requirement Document (PRD): Kotoba
**Dokumen:** prd-lengkap.md
**Status:** Active / Blueprint — Revisi 2
**Konteks Strategis (Antigravity & KMP Learning):**
Proyek Kotoba ini berfungsi sebagai *sandbox* dan purwarupa (prototype) teknis untuk penguasaan **Kotlin Multiplatform (KMP)**. Seluruh pola arsitektur, manajemen *state* lintas platform (Android/iOS), implementasi *dependency injection*, integrasi BaaS (Supabase), serta navigasi UI yang dikembangkan di sini akan dievaluasi dan diimplementasikan ke dalam *core engine* aplikasi **Antigravity**.

**Catatan Revisi:** Versi ini memperluas cakupan produk dari sekadar "melek Hiragana/Katakana" menjadi jalur belajar penuh hingga Kanji dasar dan percakapan sederhana, dengan menambahkan sistem retensi (SRS), onboarding berbasis tujuan, dan latihan menulis. Perubahan utama ditandai pada tiap bagian.

---

## 1. Ringkasan Eksekutif & Tujuan
**Kotoba** adalah aplikasi mobile berbasis Compose Multiplatform untuk belajar bahasa Jepang dari nol hingga level percakapan dasar. Menggunakan pendekatan *audio-first* dan *pop-culture* (Anime), aplikasi ini menargetkan pemula otodidak. Pengguna diajak mengenali bentuk huruf (Hiragana & Katakana) berdasarkan suara dan kosakata anime yang sudah akrab di telinga mereka, lalu secara bertahap dibawa masuk ke Kanji dasar, pola kalimat, dan latihan percakapan — tanpa merasa dibebani di tahap awal.

**Tujuan Bisnis & Teknis:**
1. Menyediakan jalur belajar bahasa Jepang yang menyenangkan (*Playful EdTech*) dengan retensi tinggi, dari nol sampai bisa membaca teks pendek dan Kanji dasar (setara JLPT N5).
2. Membangun fondasi KMP yang solid yang mencakup *local storage*, *cloud sync* (Supabase), *native hardware interop* (TTS Audio), dan sistem *spaced repetition* yang bekerja offline-first.

**Definisi Sukses (KPI Utama):** *(baru)*
* Retensi D1 / D7 / D30 (persentase user yang kembali setelah 1, 7, 30 hari).
* *Completion rate* per level (berapa % user yang lulus kuis di setiap level).
* Rata-rata jumlah item (kana/kosakata/kanji) yang "dikuasai" (status `MASTERED` di SRS) per user per minggu.
* *Streak* rata-rata (indikator kebiasaan harian).

---

## 2. Target Pengguna & Persona
* **Demografi:** Penggemar anime, *gamer*, atau penikmat *pop-culture* Jepang usia 13-35 tahun.
* **Pain Points:**
  * Familiar dengan bunyi kata (contoh: *sugoi*, *arigatou*) tapi buta huruf Jepang.
  * Aplikasi belajar konvensional membosankan dan terlalu cepat masuk ke tata bahasa yang kaku dan Kanji tanpa konteks.
  * Sering belajar tapi cepat lupa karena tidak ada sistem pengulangan — *(baru)*.
  * Bingung "belajar bahasa Jepang itu urutannya gimana, sih?" saat pertama buka aplikasi — *(baru)*.
* **Motivasi:** Ingin bisa membaca teks pendek, meme, Kanji dasar, atau memahami ucapan dasar karakter anime tanpa *subtitle*, dan pada akhirnya bisa melakukan percakapan sederhana.
* **Sub-persona berdasarkan tujuan** *(baru, dipetakan lewat onboarding quiz — lihat 4.2):*
  1. **Si Penikmat Anime** — mau ngerti dialog/meme tanpa subtitle, prioritas kosakata & Kana.
  2. **Si Pemburu Sertifikat** — mau lulus JLPT N5, prioritas Kanji & tata bahasa terstruktur.
  3. **Si Calon Traveler/Ngobrol** — mau bisa percakapan dasar, prioritas modul dialog & listening.

---

## 3. Tech Stack & Modern Dependencies (KMP)
Aplikasi ini akan menggunakan *library* KMP modern yang menjadi standar industri (dan calon *stack* untuk Antigravity):

### 3.1 Core & UI
* **UI Framework:** Compose Multiplatform (JetBrains).
* **Navigation:** `voyager-navigator` & `voyager-transitions` (Sangat stabil untuk KMP) atau *Jetpack Navigation Compose Multiplatform*.
* **Image Loading:** `coil-compose` (Coil 3.x mendukung KMP penuh).
* **Dependency Injection:** `koin-core` & `koin-compose` (Ringan dan standar de-facto KMP).
* **Concurrency:** `kotlinx-coroutines-core` & `Flow`.
* **Canvas / Drawing:** Compose `Canvas` API untuk latihan *stroke order* Kanji — *(baru, lihat 5.6)*.

### 3.2 Data & Network
* **Backend as a Service (BaaS):** Supabase KMP (`io.github.jan-tennert.supabase`).
  * `gotrue-kt`: Untuk Authentication (Login/Signup).
  * `postgrest-kt`: Untuk sinkronisasi database kustom (Progress level user, jadwal SRS).
* **Local Database:** `androidx.room:room-compiler` (Room KMP versi 2.6+) atau `sqldelight` (sebagai alternatif performa tinggi). Wajib mendukung query per-item untuk kebutuhan SRS (lihat 7.3).
* **Key-Value Storage:** `androidx.datastore:datastore-preferences` (Untuk preferensi lokal spt tema, status onboarding, hasil goal quiz, dan toggle furigana).
* **Serialization:** `kotlinx-serialization-json`.

### 3.3 Platform-Specific (expect/actual)
* **Audio TTS:** Implementasi kustom `expect/actual` (Android `TextToSpeech` API & iOS `AVSpeechSynthesizer`).
* **Speech Recognition (opsional, Fase 2):** Android `SpeechRecognizer` & iOS `SFSpeechRecognizer` untuk latihan *shadowing* — *(baru, lihat 5.7)*.

---

## 4. Alur Pengguna (User Flow Aplikasi)

Alur aplikasi dirancang untuk *onboarding* yang mulus dan retensi pengguna.

### FASE 1: Entry & Onboarding
1. **Splash Screen (Loading):**
   * Menampilkan logo Kotoba.
   * *Background task:* Memeriksa sesi token Supabase secara lokal (DataStore). Jika ada sesi → lompat ke **Home**. Jika tidak → lanjut ke **Landing Page**.
2. **Landing Page (Carousel / Walkthrough):**
   * *Slide 1:* "Belajar Bahasa Jepang dari Anime Favoritmu!" (Ilustrasi karakter).
   * *Slide 2:* "Dengar, Tebak, dan Hafalkan Kana tanpa bosan."
   * *Slide 3:* "Dari Kana sampai Kanji dan Percakapan — satu jalur, tanpa nyasar." *(diperbarui)*
   * *Slide 4:* "Mulai petualanganmu sekarang." (Tombol *Login* / *Sign Up*).

### FASE 1.5: Goal Quiz & Personalisasi *(baru)*
Ditempatkan **setelah Sign Up**, sebelum user pertama kali menyentuh Home Screen. Tujuannya: pemula tahu persis harus mulai dari mana dan kenapa.
1. **Pertanyaan 1 — Tujuan belajar:** "Kamu mau bisa apa?" → (a) Ngerti anime/meme tanpa subtitle (b) Lulus JLPT N5 (c) Ngobrol dasar buat jalan-jalan/kerja.
2. **Pertanyaan 2 — Level saat ini:** "Seberapa jauh kamu sudah kenal huruf Jepang?" → (a) Nol besar (b) Hafal sebagian Hiragana (c) Sudah hafal Kana, mau lanjut Kanji.
3. **Pertanyaan 3 — Waktu belajar:** "Berapa lama kamu mau belajar per hari?" → (a) 5 menit (b) 15 menit (c) 30+ menit.
4. **Output:** Sistem menentukan titik masuk kurikulum (lompat ke Level tertentu bila user sudah kenal Kana) dan target harian (jumlah kartu/hari), disimpan di tabel `user_goals` (lihat 7.4). Ini menghindari user yang sudah bisa Kana dipaksa mengulang dari nol.
5. **Coach Mark Interaktif:** Begitu masuk Learning Screen pertama kali, tampilkan *tooltip* singkat (bukan halaman terpisah): cara tap kartu untuk audio, cara swipe untuk "Hafal/Ulangi", dan indikator estimasi waktu ("~5 menit untuk modul ini").

### FASE 2: Autentikasi (Supabase)
1. **Sign Up (Daftar):**
   * Input Email, Password, dan Nama Panggilan.
   * Konfirmasi usia minimum (relevan karena target termasuk usia 13 tahun) dan persetujuan kebijakan privasi — *(baru, lihat 8.5)*.
   * Supabase Auth membuat entri *user*, disinkronkan ke tabel `users_profile` di *database* Supabase.
2. **Login (Masuk):**
   * Input Email & Password (atau *Google OAuth*).
   * Token JWT disimpan lokal untuk akses sesi berikutnya.

### FASE 3: Pengalaman Utama (Core Loop)
1. **Home Screen (Dashboard):**
   * **Header:** Menampilkan Avatar, Nama, dan Total Koin/XP.
   * **Hero Section:** Tombol CTA besar "Lanjutkan Belajar: Level [X]".
   * **Kotak Ulangan (Review Box):** *(baru)* Kartu terpisah yang menonjol di atas roadmap, menampilkan jumlah item yang "jatuh tempo" untuk diulang hari ini (dihitung dari `next_review_date` di tabel `user_item_progress`). Ini yang membuat app terasa proaktif, bukan cuma linear.
   * **Level Roadmap:** Menampilkan *grid* atau *list* vertikal dari Level 1 hingga Level akhir kurikulum (lihat Bagian 6). Level yang belum terbuka akan digembok. Ditambahkan penanda visual grup: "Kana", "Kosakata", "Kanji Dasar", "Percakapan" — *(baru)*, agar pemula paham peta besar tanpa harus scroll bingung.
2. **Learning Screen (Katalog & Flashcard):**
   * Pengguna memilih modul (misal: "Vokal Dasar").
   * Layar menampilkan *grid* huruf/kata. Saat kartu diklik, memutar audio *Native TTS*.
   * Menggeser (*swipe*) kartu (*Tinder-like* atau *flip*) untuk menandai "Sudah Hafal" atau "Ulangi" — hasil swipe ini menjadi input untuk algoritma SRS (lihat 5.1), bukan cuma penanda lokal.
   * Untuk modul Kanji: tab tambahan "Tulis" yang membuka kanvas *stroke order* (lihat 5.6).
3. **Quiz Screen (Boss Battle):**
   * Syarat membuka level berikutnya.
   * Audio diputar → Pengguna memilih 1 dari 4 pilihan ganda visual (untuk Kanji: bisa juga mode "tulis jawaban di kanvas").
   * **Result:** Menampilkan skor. Jika > 80%, status `is_unlocked` untuk level berikutnya di-*update* ke Room DB dan di-sinkronkan ke Supabase (PostgREST).
4. **Sesi Review Harian (Kotak Ulangan)** *(baru)*: Alur terpisah dari Learning Screen linear — mengambil item lintas-level yang jatuh tempo, dikemas seperti kuis cepat, hasilnya memperbarui jadwal SRS masing-masing item.

---

## 5. Rincian Fitur Utama

### 5.1 Sistem Spaced Repetition (SRS) *(baru — fitur inti)*
* **Fitur:** Setiap item (kana, kosakata, kanji) punya jadwal pengulangan individual, bukan status per-level.
* **Algoritma:** Menggunakan pendekatan SM-2 sederhana (Leitner box juga bisa jadi versi MVP yang lebih ringan): tiap kali user menjawab benar, interval pengulangan berikutnya diperpanjang; salah → interval direset dan `ease_factor` diturunkan.
* **Trigger:** Ditampilkan lewat "Kotak Ulangan" di Home Screen, dihitung dari item yang `next_review_date <= now()`.
* **Kenapa penting:** Tanpa ini, user akan "menyelesaikan" level tapi lupa dalam hitungan hari — ini pembeda retensi terbesar dibanding sekadar flashcard linear.

### 5.2 Modul Autentikasi & Profil (Supabase)
* **Fitur:** Pendaftaran, Masuk, Keluar, Lupa Kata Sandi.
* **Database Sync:** Menyimpan baris data progres (contoh: `user_id`, `current_level`, `total_xp`, `mastered_kana_list`, hasil goal quiz).

### 5.3 Modul Peta Level (Roadmap Progression)
* **Fitur:** Visualisasi berjenjang mirip peta *game*, dikelompokkan per fase (Kana → Kosakata → Kanji → Percakapan).
* **Logika:** Ketergantungan *unlock* (Level 2 tidak bisa diakses sebelum kuis Level 1 lulus), dengan pengecualian *skip* berdasarkan hasil goal quiz.

### 5.4 Flashcard Kana & Kosakata Interaktif
* **Fitur:** Kartu berisi Huruf/Kata (Depan) dan Romaji + Arti + contoh dari konteks anime (Belakang).
* **Animasi:** Menggunakan `AnimatedVisibility` atau `animateFloatAsState` (Compose) untuk efek 3D *Flip*.
* **Toggle Furigana** *(baru)*: Untuk item yang mengandung Kanji, furigana (bantuan baca) ditampilkan default di atas karakter dan bisa dimatikan manual seiring level user naik — memberi rasa progres yang jelas ke pemula.

### 5.5 Audio Native (Offline TTS Engine)
* **Fitur:** Pelafalan karakter menggunakan *engine* suara OS.
* **Keuntungan:** Tidak memakan *bandwidth* data pengguna dan tidak ada biaya *hosting* file audio (BaaS *Storage*).

### 5.6 Latihan Menulis Kanji / Stroke Order *(baru)*
* **Fitur:** Kanvas interaktif (Compose `Canvas`) yang menampilkan urutan goresan (*stroke order*) suatu Kanji secara animasi, lalu user menjiplak/menulis ulang dengan jari.
* **Validasi:** Versi MVP cukup validasi arah & urutan goresan dasar (bukan OCR penuh); versi lanjutan bisa memakai pengenalan pola sederhana.
* **Kenapa penting:** Tanpa latihan menulis, user hanya "mengenali" Kanji secara pasif, bukan benar-benar menguasainya — riset pembelajaran bahasa menunjukkan menulis memperkuat memori jangka panjang untuk aksara logografik.

### 5.7 Latihan Percakapan & Listening Bertingkat *(baru)*
* **Fitur:** Modul dialog pendek berbasis skenario (di sekolah, di konbini, memperkenalkan diri), disusun bertingkat: 1 kata → frasa pendek → kalimat lengkap → dialog dua arah.
* **Mode Shadowing (Fase 2, opsional):** User mendengar native audio, merekam ulang ucapannya, sistem membandingkan lewat *speech-to-text* sederhana untuk feedback kasar (bukan penilaian aksen presisi).

### 5.8 Gamifikasi Dasar
* **Fitur:** *Streak* harian, perolehan XP (Experience Points) setiap menyelesaikan kuis atau sesi review, dan efek konfeti (*confetti animation*) saat naik level.

---

## 6. Kurikulum & Struktur Level (Bite-sized Learning) *(diperluas signifikan)*

Materi dipecah menjadi modul mikro agar sesuai dengan rentang perhatian pengguna modern, disusun dalam 4 fase besar agar pemula tidak bingung "ini lagi belajar apa":

### Fase A — Fondasi Kana (Level 1-3)
1. **Level 1: Hiragana Inti (Seion)** – Vokal (a, i, u, e, o) dan konsonan utama.
2. **Level 2: Hiragana Dakuon** – Suara modifikasi (*teng-teng* dan *maru* / ga, za, da, ba, pa).
3. **Level 3: Youon & Sokuon** – Bunyi kecil dan konsonan jeda ganda (kya, ryu, chotto).

### Fase B — Kosakata & Katakana (Level 4-5)
4. **Level 4: Kosakata Ikonik Anime** – Membaca kata emosi tanpa Kanji (baka, sugoi, maji, yatta).
5. **Level 5: Katakana Dasar** – Aksara serapan (anime, ramen, meka).

### Fase C — Kanji Dasar & Tata Bahasa (Level 6-9) *(baru)*
6. **Level 6: Partikel & Frase Dasar** – Menyusun kata dasar (wa, o, no, ga).
7. **Level 7: Kanji Angka & Waktu** – ~20 Kanji dasar (satu-sepuluh, hari, bulan) dikenalkan lewat radikal & mnemonic, bukan hafalan mentah.
8. **Level 8: Kanji Kehidupan Sehari-hari** – ~30 Kanji (orang, besar/kecil, air, api, sekolah, dsb — mengikuti cakupan setara JLPT N5), dengan toggle furigana aktif.
9. **Level 9: Pola Kalimat Dasar** – Bentuk -masu (positif/negatif), kata sifat i/na, kalimat tanya sederhana.

### Fase D — Percakapan (Level 10-12) *(baru)*
10. **Level 10: Perkenalan Diri** – Dialog memperkenalkan nama, asal, hobi.
11. **Level 11: Situasi Sehari-hari** – Dialog di konbini, sekolah, tanya arah.
12. **Level 12: Listening Lanjutan & Mini-Cerita** – Kutipan dialog anime pendek (2-3 baris) tanpa subtitle, dengan pilihan toggle furigana on/off sebagai "ujian akhir mandiri".

> **Catatan Scope MVP** *(baru)*: Untuk rilis pertama, disarankan cakupan MVP hanya sampai **akhir Fase B (Level 1-5)** + sistem SRS dasar (5.1) + goal quiz (4.1.5), supaya inti pembelajaran & retensi tervalidasi dulu sebelum investasi besar di konten Kanji/percakapan (Fase C & D) yang produksi kontennya jauh lebih berat. Fase C & D masuk roadmap Fase 2 pasca-validasi MVP.

---

## 7. Skema Database (Supabase / Room KMP) *(diperluas)*

### 7.1 Tabel `users_profile` (Remote Supabase)
* `id` (UUID, Primary Key, berelasi dengan `auth.users`)
* `display_name` (String)
* `total_xp` (Int, Default 0)
* `current_streak` (Int, Default 0)
* `furigana_enabled` (Boolean, Default true) — *(baru)*

### 7.2 Tabel `user_progress` (Remote & Local Room)
* `id` (UUID)
* `user_id` (UUID, Foreign Key)
* `level_id` (String, contoh: "lvl_1_vowel")
* `status` (Enum: `LOCKED`, `IN_PROGRESS`, `COMPLETED`)
* `high_score` (Int)

### 7.3 Tabel `vocab_items` / `kanji_items` (Master Data, Remote) *(baru)*
* `id` (UUID)
* `type` (Enum: `KANA`, `VOCAB`, `KANJI`)
* `character` (String — huruf/kata/kanji)
* `reading_on` (String, nullable — untuk Kanji)
* `reading_kun` (String, nullable — untuk Kanji)
* `romaji` (String)
* `meaning` (String)
* `example_sentence` (String, nullable)
* `radical_hint` (String, nullable — mnemonic untuk Kanji)
* `stroke_order_data` (JSON, nullable — koordinat urutan goresan untuk Kanvas)
* `level_id` (Foreign Key ke modul level)

### 7.4 Tabel `user_item_progress` (Remote & Local Room, inti SRS) *(baru)*
* `id` (UUID)
* `user_id` (UUID, Foreign Key)
* `item_id` (UUID, Foreign Key ke `vocab_items`/`kanji_items`)
* `status` (Enum: `NEW`, `LEARNING`, `REVIEWING`, `MASTERED`)
* `ease_factor` (Float, Default 2.5)
* `interval_days` (Int, Default 0)
* `next_review_date` (Timestamp)
* `last_reviewed_at` (Timestamp)
* `correct_streak` (Int)

### 7.5 Tabel `user_goals` (Remote & Local, hasil onboarding) *(baru)*
* `id` (UUID)
* `user_id` (UUID, Foreign Key)
* `primary_goal` (Enum: `ANIME_COMPREHENSION`, `JLPT_N5`, `CONVERSATION`)
* `starting_level_id` (Foreign Key — hasil placement dari goal quiz)
* `daily_target_minutes` (Int)

### 7.6 Tabel `user_progress` untuk Kanji (Remote & Local Room)
* `id` (UUID)
* `user_id` (UUID, Foreign Key)
* `level_id` (String, contoh: "lvl_1_vowel")
* `status` (Enum: `LOCKED`, `IN_PROGRESS`, `COMPLETED`)
* `high_score` (Int)

---

## 8. Kebutuhan Non-Fungsional (Non-Functional Requirements)

1. **Offline Capability:** Pengguna yang sudah *login* harus bisa membuka aplikasi dan belajar/kuis/review SRS meskipun sedang *offline*. Data disinkronkan kembali saat koneksi tersedia (menerapkan pola *Repository* dengan *Local First*). Perhitungan jadwal SRS (7.4) harus bisa berjalan sepenuhnya lokal di Room, lalu disinkron ke Supabase saat online.
2. **Performa UI:** Render halus dengan Compose, menargetkan *60fps* untuk animasi pergantian layar (Voyager Transitions), *flip* kartu, dan kanvas *stroke order* di Android dan iOS.
3. **Ukuran Bundle:** Optimalisasi ukuran aplikasi agar tidak melebihi 40MB dengan meminimalisir aset gambar statis dan memanfaatkan Compose *Vector Icons* serta *Native Audio API*. Data `stroke_order_data` disimpan sebagai JSON koordinat ringan, bukan gambar/video.
4. **Skalabilitas Konten** *(baru)*: Skema `vocab_items`/`kanji_items` dirancang generik agar penambahan Fase C & D (Kanji, percakapan) di masa depan tidak memerlukan perubahan skema, cukup penambahan baris data.
5. **Privasi & Usia Minimum** *(baru)*: Karena target termasuk usia 13 tahun, alur Sign Up wajib mencantumkan persetujuan kebijakan privasi dan, bila relevan dengan yurisdiksi target pasar, mekanisme persetujuan orang tua (parental consent) untuk user di bawah usia tertentu.

---

## 9. Metrik Keberhasilan & Definisi MVP *(baru — ringkasan)*

| Cakupan | Termasuk di MVP? |
|---|---|
| Fase A & B (Kana + Kosakata, Level 1-5) | ✅ Ya |
| Sistem SRS dasar + Kotak Ulangan | ✅ Ya |
| Goal Quiz & Coach Mark onboarding | ✅ Ya |
| Toggle Furigana | ✅ Ya |
| Fase C (Kanji Dasar, Level 6-9) | ⏳ Fase 2 |
| Fase D (Percakapan, Level 10-12) | ⏳ Fase 2 |
| Stroke Order Writing (5.6) | ⏳ Fase 2 (butuh Kanji dulu) |
| Speech Recognition / Shadowing (5.7) | ⏳ Fase 3 (eksperimental) |

Prioritas ini memastikan validasi inti (apakah loop belajar + SRS bikin orang betah) selesai dulu sebelum investasi produksi konten Kanji & percakapan yang jauh lebih mahal secara konten dan desain.

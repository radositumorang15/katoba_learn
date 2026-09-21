# katoba_learn

**Kotoba (言葉)** — Aplikasi mobile berbasis **Compose Multiplatform (KMP)** untuk belajar bahasa Jepang dari nol hingga level percakapan dasar dengan pendekatan *audio-first* dan pop-culture/anime.

---

## 🚀 Fitur Utama

- **Kurikulum Terstruktur 4 Fase (12 Level):**
  - **Fase A: Fondasi Kana** (Hiragana Seion, Dakuon, Youon & Sokuon).
  - **Fase B: Kosakata & Katakana** (Kosakata Ikonik Anime & Katakana Dasar).
  - **Fase C: Kanji Dasar & Tata Bahasa** (Partikel Wa/O/No, Kanji Angka/Waktu, Kanji Sehari-hari, Pola Kalimat -masu).
  - **Fase D: Percakapan & Listening** (Perkenalan Diri, Situasi Sehari-hari/Konbini, Listening Mandiri).
- **Goal Quiz & Onboarding Personalization:**
  - Kuis penempatan 3 langkah (Tujuan belajar, pengetahuan awal, target menit/hari).
  - Penempatan level dinamis (bisa melewati Kana jika sudah paham).
  - Coach mark / panduan interaktif saat sesi belajar pertama.
- **Spaced Repetition System (SRS) & Kotak Ulangan Harian:**
  - Algoritma retensi memori jangka panjang individual per item (SM-2/Leitner).
  - Kartu Kotak Ulangan di Home Screen dengan status jatuh tempo harian.
  - Sesi review cepat berhadiah XP.
- **Furigana Toggle (ルビ ON/OFF):**
  - Menampilkan/menyembunyikan bantuan baca furigana di atas karakter/kanji.
- **Platform Audio TTS Native:**
  - Android `TextToSpeech` API dan iOS `AVSpeechSynthesizer` melalui pola `expect/actual`.
- **Backend & Database:**
  - Neon Serverless PostgreSQL & Neon Auth.
  - Tabel: `users_profile`, `user_progress`, `user_goals`, `user_item_progress`.

---

## 🛠️ Tech Stack

- **Framework:** Compose Multiplatform (JetBrains) 1.7.3
- **Kotlin:** 2.1.0
- **Target Platform:** Android (SDK 35), Desktop (JVM), iOS
- **Architecture:** MVC (Model - View - Controller)
- **Networking & Auth:** Ktor Client & Neon Auth API
- **Database:** Neon Serverless PostgreSQL

---

## 💻 Cara Menjalankan

### Desktop JVM:
```powershell
$env:JAVA_HOME = "D:\Androidstudio\jbr"
.\gradlew.bat :composeApp:run
```

### Android Debug APK:
```powershell
$env:JAVA_HOME = "D:\Androidstudio\jbr"
.\gradlew.bat :composeApp:assembleDebug
```
Output APK berlokasi di: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

# Design System & UI Architecture (Kotoba)
**Dokumen:** design.md
**Status:** Active / Blueprint
**Referensi Visual:** `image_48917c.jpg` (Playful EdTech UI)
**Konteks Strategis (Antigravity & KMP):** 
Desain sistem ini diimplementasikan menggunakan **Compose Multiplatform**. Proyek ini berfungsi sebagai *sandbox* untuk menguji kemampuan *rendering* UI lintas platform (Android & iOS). Standarisasi tema, komponen kustom, dan manajemen *state* visual yang dibangun di sini akan dijadikan referensi dan *blueprint* untuk pengembangan sistem antarmuka pada *core engine* aplikasi **Antigravity**.

---

## 1. Filosofi Desain (Playful EdTech)
Berdasarkan referensi visual, aplikasi mengusung gaya *Playful EdTech* yang dirancang agar tidak mengintimidasi, ramah pemula, dan *engaging*.
* **Karakteristik Utama:** Sudut yang sangat membulat (*extra rounded corners*), penggunaan warna solid yang berani (Hijau Emerald) dikontraskan dengan warna pastel lembut, ikonografi berbasis garis (*line-art*), dan tata letak berbasis kartu (*card-based layout*).
* **Vibe:** Menyenangkan, kasual, seperti bermain *game*.

---

## 2. Palet Warna (Color Tokens)

Warna-warna ini diekstrak langsung dari referensi UI untuk direplikasi ke dalam `lightColorScheme` Compose di modul `shared`.

### 2.1 Brand & Aksen Utama
* **Primary Green:** `#0F755E` 
  *(Digunakan untuk Hero Card "I'm your AI Tutor", tombol CTA solid, dan tab aktif)*
* **Primary Surface / Light Green:** `#E5F4F0`
  *(Digunakan untuk latar belakang kartu kategori atau badge sekunder)*

### 2.2 Warna Sekunder & Elemen Interaktif
* **Soft Purple / Lavender:** `#E8E5FE` 
  *(Digunakan untuk latar belakang Pill/Badge Koin)*
* **Deep Purple:** `#5C4EE5`
  *(Digunakan untuk teks dan ikon pada Badge Koin, memberikan kontras tinggi di atas Soft Purple)*
* **Soft Yellow / Cream:** `#FDF1D5`
  *(Digunakan untuk latar kartu kuis/pengetahuan seperti "Testing your knowledge")*
* **Pastel Card Accents:** `#FEE2E2` (Soft Coral), `#E0F2FE` (Soft Blue)

### 2.3 Netral (Background, Surface & Text)
* **App Background:** `#FFFFFF` (Putih bersih)
* **Surface / Card:** `#FFFFFF` (Putih dengan *border* tipis)
* **Text Primary (Headings):** `#1A1C20` (Hitam/Slate pekat)
* **Text Secondary (Subtitles):** `#7A7E86` (Abu-abu lembut)
* **Border & Outline:** `#E2E8F0` (Abu-abu sangat terang, 1dp)

---

## 3. Tipografi & Radius Sudut (Typography & Shapes)

Sistem ini menghindari sudut tajam untuk memperkuat kesan *playful*.

### 3.1 Font Family
* **Primary Font:** `Nunito`, `Quicksand`, atau `Poppins`. (Karakter font bulat tak bersudut kaku).
* **Weights:** 
  * *Bold* (700) untuk Judul Seksi (Section Headers) dan Judul Kartu.
  * *SemiBold* (600) untuk Teks Tombol (CTA).
  * *Medium* (500) untuk *Body* dan *Subtitle*.

### 3.2 Corner Radii (Shapes)
* **Extra Large (`28.dp` - `32.dp`):** Bottom Sheet dan Panel besar.
* **Large (`24.dp`):** Hero Card hijau utama dan kartu modul ilustrasi.
* **Medium (`16.dp` - `20.dp`):** Kartu *Grid* 2x2, Kartu *Course/Kana* vertikal, gambar *thumbnail*.
* **Pill / Fully Rounded (`CircleShape` / `50%`):** Tombol aksi utama ("Start Learning", "Enrolled Now"), Badge Koin, dan Tab tersegmentasi ("All Library", "Completed").

---

## 4. Komponen UI Inti (Antigravity Compose Blueprint)

Berikut adalah standarisasi komponen UI dari gambar yang akan diprogram dalam `commonMain`:

### 4.1 Header Bar (Top App Bar)
* **Kiri:** Avatar profil berbentuk lingkaran (`CircleShape`, ukuran `40.dp`).
* **Tengah / Kanan:** *Coin/XP Badge* berbentuk *Pill*. Latar `#E8E5FE`, ikon dan teks `#5C4EE5`, disandingkan dengan ikon lonceng notifikasi (garis tepi/outline).

### 4.2 Hero Banner Card ("AI Sensei")
* **Latar:** `Primary Green` (`#0F755E`).
* **Sudut:** `RoundedCornerShape(24.dp)`.
* **Konten:** Teks putih tebal, *subtitle* putih semi-transparan, tombol putih beraksen teks hijau (`CircleShape`). Ilustrasi 2D yang seolah "keluar" (*overlap*) dari batas kartu di sisi kanan.

### 4.3 Action Menu Grid (2x2)
* **Contoh di gambar:** *All Classes, My Courses, My Shelf, My Progress.*
* **Spesifikasi:** Kartu putih persegi dengan `border` tipis (`1.dp`, `#E2E8F0`). Latar belakang kartu `#FFFFFF`, sudut `20.dp`. Ikon menggunakan gaya *line-art* berwarna.

### 4.4 Segmented Pill Tabs
* **Contoh di gambar:** Tab "All Library" dan "Completed".
* **Spesifikasi:** Kontainer dengan sudut membulat penuh (`CircleShape`). 
  * **Status Aktif:** Latar `Primary Green`, teks putih.
  * **Status Tidak Aktif:** Latar transparan, teks `Text Secondary`.

### 4.5 Horizontal Scrolling Cards (Katalog Huruf / Vocab)
* **Contoh di gambar:** "Top rated courses" / "Continue Reading".
* **Spesifikasi:** 
  * Area gambar atas dengan sudut `16.dp` (bisa diisi blok warna pastel atau ilustrasi).
  * Area teks bawah (Judul besar, pembuat/kategori, dan baris metadata: jumlah pelajaran, harga/koin).
  * Tombol CTA di dalam kartu ("Enrolled Now" / "Mulai") berbentuk *Pill* hijau panjang (*full-width* di dalam kartu).

### 4.6 Bottom Navigation Bar
* **Gaya:** Sangat minimalis, latar putih solid tanpa bayangan keras.
* **Item:** Ikon *line-art* (Home, Statistic, Search, Setting). 
* **State Aktif:** Ikon berubah menjadi solid (berisi), warna berubah menjadi `Primary Green`.

---

## 5. Implementasi & Best Practices (Untuk Antigravity)

1. **Strict Compose Multiplatform Only:** Semua UI harus didefinisikan murni menggunakan Jetpack Compose. Tidak ada file XML (Android) atau Storyboard/SwiftUI (iOS) kecuali benar-benar dibatasi oleh OS.
2. **Material 3 Customization:** Jangan gunakan warna bawaan Material 3 (ungu default). Lakukan *override* penuh pada `lightColorScheme` agar sesuai dengan palet di atas.
3. **Reusability:** Komponen seperti `CoinBadge`, `PillButton`, dan `HeroCard` harus dibuat sebagai fungsi `@Composable` terpisah di folder `ui/components/` agar dapat dipanggil berkali-kali tanpa redudansi kode.
4. **Platform Agnostic UI:** Hindari *hardcode* nilai *padding* untuk menutupi *notch* atau *status bar*. Gunakan `WindowInsets` bawaan Compose Multiplatform agar *header* dan *bottom bar* menyesuaikan layar secara otomatis di iPhone maupun perangkat Android modern.

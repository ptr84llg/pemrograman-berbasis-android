# PresensiKelasP7 — State dan Alur Data UI

Baseline: `PresensiKelasP6` commit `9134f0d72e3a2ef73e3135210bc0b874ffbed817`.

Tujuan pembelajaran: state sementara, alur View → state → render, evaluasi kelengkapan, respons perubahan input, reset state, pemulihan sederhana pada perubahan konfigurasi, dan navigasi berbasis Intent.

Tiga layar: Ringkasan (`MainActivity`), Form dengan Live Preview (`FormActivity`), dan Konfirmasi (`ConfirmationActivity`).

Kompnen baru Form: `txtPreviewNama`, `txtPreviewNim`, `txtPreviewStatus`, `txtPreviewKesiapan`, `btnReset`. Pengelolaan state: `PresensiUiState.kt` dan fungsi pada `FormActivity.kt`.

Data tidak disimpan ke basis data atau file. Angka 14/2/1 di ringkasan adalah data contoh P5 dan belum berubah mengikuti form.

Cara menggunakan:
1. Simpan ZIP overlay dan skrip PS1 ke `D:\DOWNLOAD`.
2. Jalankan `ANDROID_P7_CREATE_FROM_P6_SAFE.ps1 -DryRun`, lalu jalankan tanpa `-DryRun` jika PASS.
3. Buka subfolder `PresensiKelasP7` sebagai project di Android Studio Rabbit 1.
4. Sync Gradle, Build, dan Run di perangkat. Jika build gagal, periksa log dependency dahulu, jangan ubah Gradle sembarangan.
5. Uji input nama, NIM, pilihan status, reset, rotasi, konfirmasi, dan kembali ke ringkasan.
6. GitHub belum berubah sampai Anda memerintahkan commit/push.

Catatan: screenshot/diagram pada PPTX dan modul adalah simulasi akademik, bukan bukti runtime P7.

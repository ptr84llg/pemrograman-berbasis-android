package com.example.presensikelasp7

/**
 * State sementara untuk layar Form Presensi.
 * Bukan entitas basis data, tidak ditulis ke penyimpanan permanen.
 */
data class PresensiUiState(
    val nama: String = "",
    val nim: String = "",
    val status: String = ""
) {
    val siapDikirim: Boolean
        get() = nama.isNotBlank() && nim.isNotBlank() && status.isNotBlank()
}

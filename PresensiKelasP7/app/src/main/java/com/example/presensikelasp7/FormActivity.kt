package com.example.presensikelasp7

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** P7: View -> state -> render UI. State hanya sementara dan tanpa database. */
class FormActivity : AppCompatActivity() {
    private lateinit var inputNama: EditText
    private lateinit var inputNim: EditText
    private lateinit var radioStatus: RadioGroup
    private lateinit var previewNama: TextView
    private lateinit var previewNim: TextView
    private lateinit var previewStatus: TextView
    private lateinit var previewKesiapan: TextView
    private lateinit var tombolKirim: Button

    private var uiState = PresensiUiState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainForm)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        inputNama = findViewById(R.id.inputNama)
        inputNim = findViewById(R.id.inputNim)
        radioStatus = findViewById(R.id.radioStatus)
        previewNama = findViewById(R.id.txtPreviewNama)
        previewNim = findViewById(R.id.txtPreviewNim)
        previewStatus = findViewById(R.id.txtPreviewStatus)
        previewKesiapan = findViewById(R.id.txtPreviewKesiapan)
        tombolKirim = findViewById(R.id.btnSubmit)

        // State tersimpan hanya untuk pemulihan Activity, bukan penyimpanan lokal.
        if (savedInstanceState != null) {
            inputNama.setText(savedInstanceState.getString(KEY_NAMA).orEmpty())
            inputNim.setText(savedInstanceState.getString(KEY_NIM).orEmpty())
            when (savedInstanceState.getString(KEY_STATUS).orEmpty()) {
                "Hadir" -> radioStatus.check(R.id.radioHadir)
                "Izin" -> radioStatus.check(R.id.radioIzin)
                "Sakit" -> radioStatus.check(R.id.radioSakit)
                else -> radioStatus.clearCheck()
            }
        }

        val pemantauInput = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
            override fun afterTextChanged(s: Editable?) { perbaruiStateDariForm() }
        }
        inputNama.addTextChangedListener(pemantauInput)
        inputNim.addTextChangedListener(pemantauInput)
        radioStatus.setOnCheckedChangeListener { _, _ -> perbaruiStateDariForm() }

        findViewById<Button>(R.id.btnReset).setOnClickListener {
            inputNama.text.clear()
            inputNim.text.clear()
            radioStatus.clearCheck()
            perbaruiStateDariForm()
        }
        tombolKirim.setOnClickListener {
            if (!uiState.siapDikirim) return@setOnClickListener
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra(ConfirmationActivity.EXTRA_NAMA, uiState.nama)
                putExtra(ConfirmationActivity.EXTRA_NIM, uiState.nim)
                putExtra(ConfirmationActivity.EXTRA_STATUS, uiState.status)
            }
            startActivity(intent)
            finish() // Menjaga pola navigasi P6 ke ringkasan.
        }

        perbaruiStateDariForm()
    }

    /** Ambil data View -> susun state baru -> render ulang tampilan. */
    private fun perbaruiStateDariForm() {
        val status = when (radioStatus.checkedRadioButtonId) {
            R.id.radioHadir -> "Hadir"
            R.id.radioIzin -> "Izin"
            R.id.radioSakit -> "Sakit"
            else -> ""
        }
        uiState = PresensiUiState(
            nama = inputNama.text.toString().trim(),
            nim = inputNim.text.toString().trim(),
            status = status
        )
        tampilkanState()
    }

    /** Render hanya membaca uiState, tidak mengambil ulang data dari EditText. */
    private fun tampilkanState() {
        previewNama.text = uiState.nama.ifEmpty { "Belum diisi" }
        previewNim.text = uiState.nim.ifEmpty { "Belum diisi" }
        previewStatus.text = uiState.status.ifEmpty { "Belum dipilih" }
        previewKesiapan.text = if (uiState.siapDikirim) "Data siap dikirim" else "Data belum lengkap"
        previewKesiapan.setTextColor(
            android.graphics.Color.parseColor(if (uiState.siapDikirim) "#FF15803D" else "#FF6B7280")
        )
        tombolKirim.isEnabled = uiState.siapDikirim
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_NAMA, uiState.nama)
        outState.putString(KEY_NIM, uiState.nim)
        outState.putString(KEY_STATUS, uiState.status)
        super.onSaveInstanceState(outState)
    }

    private companion object {
        const val KEY_NAMA = "ui_nama"
        const val KEY_NIM = "ui_nim"
        const val KEY_STATUS = "ui_status"
    }
}

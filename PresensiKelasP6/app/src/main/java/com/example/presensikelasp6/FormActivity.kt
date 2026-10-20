package com.example.presensikelasp6

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** Form sederhana: EditText, RadioGroup, event klik, dan validasi input. */
class FormActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_form)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainForm)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val inputNama = findViewById<EditText>(R.id.inputNama)
        val inputNim = findViewById<EditText>(R.id.inputNim)
        val radioStatus = findViewById<RadioGroup>(R.id.radioStatus)

        findViewById<Button>(R.id.btnSubmit).setOnClickListener {
            val nama = inputNama.text.toString().trim()
            val nim = inputNim.text.toString().trim()

            if (nama.isEmpty()) {
                inputNama.error = "Nama wajib diisi"
                inputNama.requestFocus()
                return@setOnClickListener
            }
            if (nim.isEmpty()) {
                inputNim.error = "NIM wajib diisi"
                inputNim.requestFocus()
                return@setOnClickListener
            }
            val selectedId = radioStatus.checkedRadioButtonId
            if (selectedId == -1) {
                Toast.makeText(this, "Pilih status kehadiran", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val status = findViewById<RadioButton>(selectedId).text.toString()
            val intent = Intent(this, ConfirmationActivity::class.java).apply {
                putExtra(ConfirmationActivity.EXTRA_NAMA, nama)
                putExtra(ConfirmationActivity.EXTRA_NIM, nim)
                putExtra(ConfirmationActivity.EXTRA_STATUS, status)
            }
            startActivity(intent)
            finish() // Tombol kembali pada konfirmasi akan menuju ringkasan P5.
        }
    }
}

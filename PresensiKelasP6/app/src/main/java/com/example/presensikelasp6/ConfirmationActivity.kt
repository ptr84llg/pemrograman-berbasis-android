package com.example.presensikelasp6

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/** Hanya menampilkan data lewat Intent. Tidak ada penyimpanan permanen. */
class ConfirmationActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_NAMA = "extra_nama"
        const val EXTRA_NIM = "extra_nim"
        const val EXTRA_STATUS = "extra_status"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_confirmation)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainConfirmation)) { view, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        val nama = intent.getStringExtra(EXTRA_NAMA).orEmpty()
        val nim = intent.getStringExtra(EXTRA_NIM).orEmpty()
        val status = intent.getStringExtra(EXTRA_STATUS).orEmpty()
        if (nama.isBlank() || nim.isBlank() || status.isBlank()) {
            finish() // Tidak menampilkan konfirmasi bila parameter tidak lengkap.
            return
        }

        findViewById<TextView>(R.id.txtResultNama).text = nama
        findViewById<TextView>(R.id.txtResultNim).text = nim
        findViewById<TextView>(R.id.txtResultStatus).text = status
        findViewById<Button>(R.id.btnBackToHome).setOnClickListener { finish() }
    }
}

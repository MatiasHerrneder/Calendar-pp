package com.matiasherrneder.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import com.matiasherrneder.calendar.databinding.VerTareaBinding

class VerTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var verTareaBind = VerTareaBinding.inflate(layoutInflater)
        setContentView(verTareaBind.root)

        val titulo = intent.getStringExtra("titulo")
        val descripcion = intent.getStringExtra("descripcion")

        verTareaBind.apply {
            tvTitulo.setText(titulo)
            tvDescripcion.setText(descripcion)

            btTitulo.setOnClickListener {
                tvTitulo.isEnabled = !tvTitulo.isEnabled
                if (tvTitulo.isEnabled) {
                    tvTitulo.setSelection(tvTitulo.text.length)
                    tvTitulo.requestFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(tvTitulo, InputMethodManager.SHOW_IMPLICIT)
                }
            }

            btDescripcion.setOnClickListener {
                tvDescripcion.isEnabled = !tvDescripcion.isEnabled
                if (tvDescripcion.isEnabled) {
                    tvDescripcion.setSelection(tvDescripcion.text.length)
                    tvDescripcion.requestFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(tvDescripcion, InputMethodManager.SHOW_IMPLICIT)
                }
            }

            btListo.setOnClickListener {
                val res = Intent()
                val nTitulo = tvTitulo.text.toString()
                val nDescripcion = tvDescripcion.text.toString()
                if (titulo != nTitulo) {
                    res.putExtra("titulo", nTitulo)
                }
                if (descripcion != nDescripcion) {
                    res.putExtra("descripcion", nDescripcion)
                }
                if (cbTerminada.isChecked) {
                    res.putExtra("terminada", true)
                }
                res.putExtra("id", intent.getIntExtra("id", -1))
                setResult(Activity.RESULT_OK, res)
                finish()
            }
        }
    }
}
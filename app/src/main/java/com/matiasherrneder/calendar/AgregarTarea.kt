package com.matiasherrneder.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.matiasherrneder.calendar.databinding.AgregarTareaBinding

class AgregarTarea : ComponentActivity() {
    private lateinit var agregarTareaBind: AgregarTareaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        agregarTareaBind = AgregarTareaBinding.inflate(layoutInflater)
        setContentView(agregarTareaBind.root)
        agregarTareaBind.btCargar.setOnClickListener {
            val res = Intent()
            var titl = agregarTareaBind.etTitulo.text.toString()
            if (titl != "") {
                res.putExtra("titulo", titl)
                res.putExtra("descripcion", agregarTareaBind.etDescripcion.text.toString())
                setResult(Activity.RESULT_OK, res)
                finish()
            }
        }
    }


}
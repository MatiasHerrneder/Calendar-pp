package com.matiasherrneder.calendar

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import com.matiasherrneder.calendar.databinding.AnimacionTareaListaBinding

class AnimacionTareaLista: ComponentActivity() {

    private lateinit var animacionTareaListaBind: AnimacionTareaListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        animacionTareaListaBind = AnimacionTareaListaBinding.inflate(layoutInflater)
        setContentView(animacionTareaListaBind.root)

        val cantTareas = intent.getIntExtra("cantidadFinalizadas", 0)
        animacionTareaListaBind.tvMensajeFinalizadas.text = getString(R.string.tareasFinalizadas, cantTareas)

        val delay: Long = 2000
        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }, delay)
    }
}
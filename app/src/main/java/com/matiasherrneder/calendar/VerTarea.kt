package com.matiasherrneder.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.matiasherrneder.calendar.databinding.VerTareaBinding

class VerTarea : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var verTareaBind = VerTareaBinding.inflate(layoutInflater)
        setContentView(verTareaBind.root)

        verTareaBind.btListo.setOnClickListener {
            finish()
        }
    }
}
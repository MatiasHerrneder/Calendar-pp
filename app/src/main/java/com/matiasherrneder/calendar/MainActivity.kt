package com.matiasherrneder.calendar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.matiasherrneder.calendar.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {

    private lateinit var activityMainBind: ActivityMainBinding
    private lateinit var listaAdapter: ListaAdapter

    private val agregarTareaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val titl = data?.getStringExtra("titulo")
            val desc = data?.getStringExtra("descripcion")
            if (titl != null && desc != null) listaAdapter.agregarItemLista(ItemLista(titl, desc))
        }
    }

    private val verTareaResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            var titulo = data?.getStringExtra("titulo")
            var descripcion = data?.getStringExtra("descripcion")
            val terminada = data?.getBooleanExtra("terminada", false)
            val id = data?.getIntExtra("id", -1)
            if (titulo == null) titulo = ""
            if (descripcion == null) descripcion = ""
            if (id != null && terminada != null) {
                listaAdapter.actualizarItemLista(id, titulo, descripcion, terminada)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBind.root)
        listaAdapter = ListaAdapter(mutableListOf(), this)
        activityMainBind.rvItems.adapter = listaAdapter
        activityMainBind.rvItems.layoutManager = LinearLayoutManager(this)

        activityMainBind.bAgregar.setOnClickListener {
            val intent = Intent(this, AgregarTarea::class.java)
            agregarTareaResultLauncher.launch(intent)
        }

        activityMainBind.bListo.setOnClickListener {
            var cantFinalizadas = 0
            listaAdapter.leerLista()
            listaAdapter.let {
                for (i in 0 until it.itemCount) {
                    val vh = activityMainBind.rvItems.findViewHolderForAdapterPosition(i) as? ListaAdapter.ListaViewHolder
                    vh?.let { viewHolder ->
                        if (viewHolder.binding.cbCheck.isChecked) {
                            it.finalizarItemLista(i)
                            cantFinalizadas++
                        }
                    }
                }
            }
            animarFinalizarTareas(cantFinalizadas)
            listaAdapter.finalizarTareas()
            mostrarBotonFinalizar(false)
        }
    }

    fun crearActividadVerTarea(item: ItemLista, pos: Int) {
        val intent = Intent(this, VerTarea::class.java)
        intent.putExtra("titulo", item.titulo)
        intent.putExtra("descripcion", item.descripcion)
        intent.putExtra("id", pos)
        verTareaResultLauncher.launch(intent)
    }

    fun animarFinalizarTareas(cantTareas: Int) {
        finish()
        val intent = Intent(this, AnimacionTareaLista::class.java)
        intent.putExtra("cantidadFinalizadas", cantTareas)
        startActivity(intent)
    }

    fun mostrarBotonFinalizar(mostrar :Boolean = true) {
        if (mostrar) {
            activityMainBind.bListo.visibility = View.VISIBLE
            activityMainBind.bAgregar.visibility = View.GONE
        }
        else {
            activityMainBind.bListo.visibility = View.GONE
            activityMainBind.bAgregar.visibility = View.VISIBLE
        }
    }

    fun ningunoCheckeado(): Boolean {
        activityMainBind.rvItems.adapter.let { listaAdapter ->
            if (listaAdapter != null) {
                for (i in 0 until listaAdapter.itemCount) {
                    val viewHolder = activityMainBind.rvItems.findViewHolderForAdapterPosition(i) as? ListaAdapter.ListaViewHolder
                    if (viewHolder?.binding?.cbCheck?.isChecked == true) return false
                }
            }
        }
        return true
    }

}
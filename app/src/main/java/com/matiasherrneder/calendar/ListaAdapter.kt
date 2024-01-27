package com.matiasherrneder.calendar

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.matiasherrneder.calendar.databinding.ItemTareaBinding
import java.io.File

class ListaAdapter(
    private var lista: MutableList<ItemLista>, private val pap: MainActivity
) : RecyclerView.Adapter<ListaAdapter.ListaViewHolder>() {
    class ListaViewHolder(val binding: ItemTareaBinding) : RecyclerView.ViewHolder(binding.root)

    private val nombreFil = "fileTest"
    private val context = pap as Context

    fun actualizarLista() {
        leerLista()
        lista = lista.filter {!it.terminada}.toMutableList()
        notifyDataSetChanged()
    }

    fun leerLista() {
        val file = File(context.filesDir, nombreFil)
        if (!file.exists()) {
            file.createNewFile()
        }
        lista = file.readMutableList()
    }

    init {
        actualizarLista()
    }

    fun MutableList<ItemLista>.toJsonString(): String {
        val gson = Gson()
        return gson.toJson(this)
    }

    fun MutableList<ItemLista>.saveToFile(file: File) {
        val jsonString = this.toJsonString()
        file.writeText(jsonString)
    }

    fun String?.toMutableList(): MutableList<ItemLista> {
        if (this == null) {
            return mutableListOf()
        }
        val gson = Gson()
        val itemType = object : TypeToken<MutableList<ItemLista>>() {}.type
        return gson.fromJson(this, itemType) ?: mutableListOf()
    }

    fun File.readMutableList(): MutableList<ItemLista> {
        val jsonString = readText()
        return jsonString.toMutableList()
    }

    fun agregarItemLista(item: ItemLista) {
        val file = File(context.filesDir, nombreFil)
        leerLista()
        lista.add(item)
        lista.saveToFile(file)
        actualizarLista()
    }

    fun finalizarItemLista(fin: Int) {
        var c = -1
        var i = 0
        while (c < fin) {
            if (!lista[i].terminada) c++
            else i++
        }
        lista[i].terminada = true
    }

    fun finalizarTareas() {
        val file = File(context.filesDir, nombreFil)
        lista.saveToFile(file)
        actualizarLista()
    }

    fun actualizarItemLista(id: Int, titulo: String, descripcion: String, terminada: Boolean) {
        val file = File(context.filesDir, nombreFil)
        var item = lista[id]
        if (titulo != "") item.titulo = titulo
        if (descripcion != "") item.descripcion = descripcion
        if (terminada != item.terminada) item.terminada = !item.terminada
        lista.saveToFile(file)
        actualizarLista()
    }

    private fun verTarea(item: ItemLista) {
        leerLista() //para actualizar bien al cerrar la actividad
        pap.crearActividadVerTarea(item, lista.indexOf(item))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaViewHolder {
        val binding = ItemTareaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListaViewHolder, position: Int) {
        val item = lista[position]

        holder.itemView.setOnClickListener {
            verTarea(item)
        }

        holder.binding.apply {
            tvTitulo.text = item.titulo

            tvTitulo.setOnClickListener {
                verTarea(item)
            }

            cbCheck.isChecked = item.terminada
            cbCheck.setOnCheckedChangeListener { _, _ ->
                if (cbCheck.isChecked) {
                    pap.mostrarBotonFinalizar()
                } else {
                    if (pap.ningunoCheckeado()) {
                        pap.mostrarBotonFinalizar(false)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}

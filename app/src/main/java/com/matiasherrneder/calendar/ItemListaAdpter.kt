package com.matiasherrneder.calendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemListaAdpter(
    private val lista: MutableList<ItemLista>
) : RecyclerView.Adapter<ItemListaAdpter.ItemListaViewHolder>() {

    class ItemListaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitulo: TextView = itemView.findViewById(R.id.tvTitulo)
        val cbCheck: CheckBox = itemView.findViewById(R.id.cbCheck)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListaViewHolder {
        return ItemListaViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tarea,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemListaViewHolder, position: Int) {
        val item = lista[position]
        holder.apply {
            tvTitulo.text = item.titulo
            cbCheck.isChecked = item.terminada
            cbCheck.setOnCheckedChangeListener { _, _ ->
                item.terminada = !item.terminada
            }
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}
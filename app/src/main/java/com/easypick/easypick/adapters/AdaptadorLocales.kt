package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easypick.easypick.Locales
import com.easypick.easypick.R
import kotlinx.android.synthetic.main.card_locales.view.*

class AdaptadorLocales(var list: List<Locales>): RecyclerView.Adapter<AdaptadorLocales.ViewHolder>(){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista=  LayoutInflater.from(parent?.context).inflate(R.layout.card_locales, parent, false)

        return ViewHolder(vista)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }


    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindItems(data: Locales) {
            val titulo: TextView = itemView.findViewById(R.id.tituloLocal)
            val detalle: TextView = itemView.findViewById(R.id.detalleLocal)
            val foto: ImageView = itemView.findViewById(R.id.imagelocal)

            titulo.text = data.titulo
            detalle.text = data.detalle

            Glide.with(itemView.context).load(data.foto).into(foto)
            itemView.setOnClickListener{
                Toast.makeText(itemView.context, "Resto de ${data.titulo}", Toast.LENGTH_LONG).show()

            }

        }
    }







}
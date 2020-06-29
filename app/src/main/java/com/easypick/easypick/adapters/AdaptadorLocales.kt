package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.Locales
import com.easypick.easypick.R

class AdaptadorLocales(var list: List<Locales>, var listener: ClickListener): RecyclerView.Adapter<AdaptadorLocales.ViewHolder>(){

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val vista=  LayoutInflater.from(parent?.context).inflate(R.layout.card_locales, parent, false)

        return ViewHolder(vista, listener)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }


    class ViewHolder(view: View, listener: ClickListener): RecyclerView.ViewHolder(view), View.OnClickListener {

        var listener: ClickListener? = null
        init {
            this.listener = listener
            view.setOnClickListener(this)
        }


        fun bindItems(data: Locales) {
            val titulo: TextView = itemView.findViewById(R.id.tituloLocal)
            val detalle: TextView = itemView.findViewById(R.id.detalleLocal)
            val foto: ImageView = itemView.findViewById(R.id.categoryImage)


            titulo.text = data.titulo
            detalle.text = data.detalle


            Glide.with(itemView.context).load(data.foto).into(foto)
//
//            itemView.setOnClickListener{
//                Toast.makeText(itemView.context, "Resto de ${data.titulo}", Toast.LENGTH_LONG).show()
//
//            }

        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }
    }







}
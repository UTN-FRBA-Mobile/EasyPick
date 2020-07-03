package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.ItemOrder
import com.squareup.picasso.Picasso

class CarritoAdapter(var items: ArrayList<ItemOrder>, var ClickListener: ClickListener): RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent?.context).inflate(R.layout.template_producto_carrito, parent, false)
        val viewHolder = CarritoAdapter.ViewHolder(vista, ClickListener)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CarritoAdapter.ViewHolder, position: Int) {
        val item = items?.get(position)
        val urlImage = item?.image
       // holder.foto?.setImageResource(item?.foto!!)
        holder.descripcion?.text =item?.description
        holder.importe?.text = item?.importe.toString()
        holder.cantidad?.text = item?.cantidad.toString()

        Picasso.get().load(urlImage).into(holder.foto!!)
    }


    class ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener {
        var vista = vista
        var foto: ImageView?= null
        var descripcion: TextView?= null
        var importe: TextView?= null
        var cantidad: TextView?= null
        var boton: Button?= null
        var listener: ClickListener?= null
        init{
            foto = vista.findViewById(R.id.iProductocarrito)
            descripcion = vista.findViewById(R.id.descripcioncarrito)
            importe = vista.findViewById(R.id.preciocarrito)
            cantidad = vista.findViewById(R.id.cantidad)
            boton = vista.findViewById(R.id.btn_quitar)

            this.listener = listener

            boton?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }

        }
}
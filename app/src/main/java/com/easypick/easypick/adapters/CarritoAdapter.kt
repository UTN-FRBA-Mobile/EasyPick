package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Producto

class CarritoAdapter(var items: List<Producto>, var ClickListener: ClickListener): RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {
    var seleccion = false
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
        holder.foto?.setImageResource(item?.foto!!)
        holder.descripcion?.text =item?.descripcion
        holder.precio?.text = item?.precio.toString()
    }


    class ViewHolder(vista: View, listener: ClickListener): RecyclerView.ViewHolder(vista), View.OnClickListener {
        var vista = vista
        var foto: ImageView?= null
        var descripcion: TextView?= null
        var precio: TextView?= null
        var boton: Button?= null
        var listener: ClickListener?= null
        init{
            foto = vista.findViewById(R.id.iProductocarrito)
            descripcion = vista.findViewById(R.id.descripcioncarrito)
            precio = vista.findViewById(R.id.preciocarrito)
            boton = vista.findViewById(R.id.btn_quitar)

            this.listener = listener

            boton?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }

        }
}
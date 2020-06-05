package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.LongClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Producto

class CarritoAdapter(var items: List<Producto>, var longClickListener: LongClickListener): RecyclerView.Adapter<CarritoAdapter.ViewHolder>() {
    var seleccion = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent?.context).inflate(R.layout.template_producto_carrito, parent, false)
        val viewHolder = CarritoAdapter.ViewHolder(vista, longClickListener)
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

    fun iniciarActionMode(){
        seleccion = true
    }

    fun destruirActionMode(){
        seleccion = false
        notifyDataSetChanged()
    }

    fun terminarActionMode(){
        seleccion = false
    }

    class ViewHolder(vista: View, longClickListener: LongClickListener): RecyclerView.ViewHolder(vista), View.OnLongClickListener {
        var vista = vista
        var foto: ImageView?= null
        var descripcion: TextView?= null
        var precio: TextView?= null
        var longListener: LongClickListener?= null
        init{
            foto = vista.findViewById(R.id.iProductoCarrito)
            descripcion = vista.findViewById(R.id.descripcionCarrito)
            precio = vista.findViewById(R.id.precioProductoCarrito)

            this.longListener = longClickListener

            vista.setOnLongClickListener(this)
        }

        override fun onLongClick(v: View?): Boolean {
            this.longListener?.longClick(v!!, adapterPosition)
            return true
        }
    }
}
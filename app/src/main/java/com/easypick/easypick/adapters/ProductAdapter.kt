package com.easypick.easypick.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Producto
import com.squareup.picasso.Picasso

class ProductAdapter(var items: List<Producto>, var listener: ClickListener): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    var contexto: Context?= null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent?.context).inflate(R.layout.template_producto, parent, false)
        contexto = parent?.context
        val viewHolder = ViewHolder(vista, listener)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
    val item = items?.get(position)
    val urlImage = item?.image
    //holder.foto?.setImageResource(item?.foto!!)
    holder.comentario?.text =item?.description
    holder.precio?.text = item?.price.toString()
    Picasso.get().load(Uri.parse(urlImage)).into(holder.imagen!!)
    }

    class ViewHolder(vista: View, listener: ClickListener) : RecyclerView.ViewHolder(vista), View.OnClickListener{
        var vista = vista
        var precio: TextView?= null
        var imagen:ImageView?= null
        var comentario: TextView?= null
        var boton: Button?= null
        var listener:ClickListener?= null

        init {
            precio = vista.findViewById(R.id.precio)
            imagen= vista.findViewById(R.id.iProducto)
            comentario = vista.findViewById(R.id.descripcion)
            boton = vista.findViewById(R.id.btn_agregar)
            this.listener = listener

            boton?.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }
    }
}

package com.easypick.easypick.adapters

import android.content.Context
import android.net.Uri
import android.util.Log
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
import com.google.zxing.client.result.URIResultParser
import com.squareup.picasso.Picasso
import java.net.URI

class ProductAdapter(var items: List<Producto>, var listener: ClickListener): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista =
            LayoutInflater.from(parent?.context).inflate(R.layout.template_producto, parent, false)
        val viewHolder = ViewHolder(vista, listener)
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
    val item = items?.get(position)
    val urlImage = item?.image


    holder.comentario?.text =item?.description
    holder.precio?.text = item?.price.toString()
    Picasso.get().load(Uri.parse(urlImage)).resize(120,0).error(R.drawable.no_food_image).into(holder.imagen)
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
            this.imagen= vista.findViewById(R.id.iProducto)
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

package com.easypick.easypick.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Producto

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

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
    val item = items?.get(position)
    holder.foto?.setImageResource(item?.foto!!)
    holder.descripcion?.text =item?.descripcion
    holder.precio?.text = item?.precio.toString()
    }

    class ViewHolder(vista: View, listener: ClickListener) : RecyclerView.ViewHolder(vista), View.OnClickListener{
        var vista = vista
        var foto:ImageView?= null
        var descripcion: TextView?= null
        var precio: TextView?= null
        //var boton: Button?= null
        var listener:ClickListener?= null

        init {
            foto= vista.findViewById(R.id.iProducto)
            descripcion = vista.findViewById(R.id.descripcion)
            precio = vista.findViewById(R.id.precio)
           // boton = vista.findViewById(R.id.agregar)
            this.listener = listener

            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
           /* boton?.setOnClickListener{
                this.listener?.onCLick(v!!, adapterPosition)
            }*/
            this.listener?.onCLick(v!!, adapterPosition)
        }

       /* fun bindItems(data: Producto) {
            val descripcion: TextView = itemView.findViewById(R.id.descripcion)
            val precio: TextView = itemView.findViewById(R.id.precio)
            val foto: ImageView = itemView.findViewById((R.id.iProducto))
            val boton: Button = itemView.findViewById(R.id.agregar)


            descripcion.text = data.descripcion
            precio.text = data.precio.toString()

            foto.setImageResource(data.foto!!)
            itemView.setOnClickListener {
                Toast.makeText(
                    itemView.context,
                    "Has Para agregar ${data.descripcion} al carrito pulse Agregar",
                    Toast.LENGTH_LONG
                ).show()
            }
            boton.setOnClickListener{
                Toast.makeText(
                    itemView.context,
                    "Has agregado ${data.descripcion} al carrito",
                    Toast.LENGTH_LONG
                ).show()
            }
        }*/
    }
}

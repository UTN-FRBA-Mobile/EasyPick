package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Order
import java.text.SimpleDateFormat
import java.util.*

class OrdersAdapter(var orders: List<Order>, var listener: ClickListener):
    RecyclerView.Adapter<OrdersAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return orders.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent?.context).inflate(R.layout.order_card,
            parent, false)
        return ViewHolder(vista, listener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(orders[position])
    }

    class ViewHolder(view: View, listener: ClickListener): RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var listener: ClickListener? = null

        init {
            this.listener = listener
            view.setOnClickListener(this)
        }

        fun bindItem(order: Order) {
            val titulo: TextView = itemView.findViewById(R.id.orderLocal)
            val fecha: TextView = itemView.findViewById(R.id.orderFecha)
            val precio: TextView = itemView.findViewById(R.id.orderPrice)
            val foto: ImageView = itemView.findViewById(R.id.orderPicture)

            titulo.text = "Local"  //order.local.titulo
            if (order.timestamp != null){
                fecha.text = SimpleDateFormat("dd/MM/yy K:m a ", Locale.ENGLISH).format(
                    order.timestamp)
            }
            else{
                fecha.text = ""
            }
            precio.text = "$" + order.getPrecio().toString()
                                            // data.local.foto
            Glide.with(itemView.context).load(R.drawable.resto1).into(foto)

            itemView.setOnClickListener{
                listener?.onCLick(it!!, adapterPosition)
            }

        }

        override fun onClick(view: View?) {
            this.listener?.onCLick(view!!, adapterPosition)
        }
    }
}
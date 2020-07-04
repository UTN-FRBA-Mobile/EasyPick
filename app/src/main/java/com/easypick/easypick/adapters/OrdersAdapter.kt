package com.easypick.easypick.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Order
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

class OrdersAdapter(var orders: List<Order>, var context: Context, var listener: ClickListener):
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
        holder.bindItem(orders[position], this.context)
    }

    class ViewHolder(view: View, listener: ClickListener): RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var listener: ClickListener? = null

        init {
            this.listener = listener
            view.setOnClickListener(this)
        }

        fun bindItem(order: Order, context: Context) {
            val titulo: TextView = itemView.findViewById(R.id.orderLocal)
            val fecha: TextView = itemView.findViewById(R.id.orderFecha)
            val precio: TextView = itemView.findViewById(R.id.orderPrice)
            val foto: CircleImageView = itemView.findViewById(R.id.orderPicture)

            titulo.text = order.local
            if (order.timestamp != null){
                fecha.text = SimpleDateFormat("dd/MM/yy", Locale.ENGLISH).format(
                    order.timestamp)
            }
            else{
                fecha.text = ""
            }
            precio.text = context.resources.getString(R.string.costo_solo,
                NumberFormat.getNumberInstance(Locale.GERMAN).format(order.costo))
            Picasso.get().load(Uri.parse(order.items?.first()?.imageURL)).
                resize(120,0).error(R.drawable.hamb_fritas).into(foto)
            if (order.completed){
                foto.borderColor = context.resources.getColor(R.color.orderComplete)
            }
            else {
                foto.borderColor = context.resources.getColor(R.color.button_color)
            }

            itemView.setOnClickListener{
                listener?.onCLick(it!!, adapterPosition)
            }

        }

        override fun onClick(view: View?) {
            this.listener?.onCLick(view!!, adapterPosition)
        }
    }
}
package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Category
import com.easypick.easypick.viewModels.CategoryViewModel


class CategoryAdapter(private val list: List<Category>, var listener:ClickListener)
        : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val vista = LayoutInflater.from(parent?.context).inflate(R.layout.fragment_local_categoria, parent, false)
            val viewHolder = CategoryAdapter.ViewHolder(vista, listener)
            return viewHolder
        }

        override fun getItemCount(): Int = list.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = list?.get(position)
            holder.name?.text = item?.name
            holder.description?.text = item?.description
        }

    class ViewHolder(vista: View, listener: ClickListener) : RecyclerView.ViewHolder(vista), View.OnClickListener{
        var vista = vista
        var name: TextView?= null
        var description: TextView?= null
        var listener: ClickListener?=null

        init{
            name = vista.findViewById(R.id.name)
            description = vista.findViewById(R.id.description)
            this.listener = listener

            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }
    }
    }

package com.easypick.easypick.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.model.Category
import com.squareup.picasso.Picasso


class CategoryAdapter(private val list: List<Category>, var listener: ClickListener) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_local_categoria, parent, false)
        return ViewHolder(vista, listener)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]


        var urlImage = item.image
        if (urlImage==null)
            urlImage = "error";

        holder.name?.text = item.name
        holder.description?.text = item.description


        Picasso.get().load(Uri.parse(urlImage)).resize(120,0).error(R.drawable.no_food_image).into(holder.image)
    }

    class ViewHolder(view: View, listener: ClickListener) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        var name: TextView? = null
        var description: TextView? = null
        var image: ImageView? = null;
        var listener: ClickListener? = null

        init {

            name = view.findViewById(R.id.categoryName)
            description = view.findViewById(R.id.categoryDescription)
            image = view.findViewById(R.id.categoryImage)

            this.listener = listener

            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onCLick(v!!, adapterPosition)
        }
    }
}

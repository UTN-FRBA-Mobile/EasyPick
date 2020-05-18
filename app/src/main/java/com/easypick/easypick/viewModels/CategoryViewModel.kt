package com.easypick.easypick.viewModels

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.R
import com.easypick.easypick.model.Category

class CategoryViewModel(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_local_categoria, parent, false)) {
    private var mNameView: TextView? = null
    private var mDescriptionView: TextView? = null


    init {
        mNameView = itemView.findViewById(R.id.name)
        mDescriptionView = itemView.findViewById(R.id.description)
    }

    fun bind(category: Category) {
        mNameView?.text = category.name
        mDescriptionView?.text = category.description;
    }

}
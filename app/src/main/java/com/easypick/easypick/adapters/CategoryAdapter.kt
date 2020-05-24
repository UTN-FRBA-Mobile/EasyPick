package com.easypick.easypick.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.model.Category
import com.easypick.easypick.viewModels.CategoryViewModel


class CategoryAdapter(private val list: List<Category>)
        : RecyclerView.Adapter<CategoryViewModel>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewModel {
            val inflater = LayoutInflater.from(parent.context)
            return CategoryViewModel(inflater, parent)
        }

        override fun onBindViewHolder(holder: CategoryViewModel, position: Int) {
            val category: Category = list[position]
            holder.bind(category)
        }

        override fun getItemCount(): Int = list.size

    }

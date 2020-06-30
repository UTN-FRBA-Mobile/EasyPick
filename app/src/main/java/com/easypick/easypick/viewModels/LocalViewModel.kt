package com.easypick.easypick.viewModels

import androidx.lifecycle.ViewModel
import com.easypick.easypick.model.Category
import com.easypick.easypick.model.ItemOrder

class LocalViewModel: ViewModel() {
    var categoria = ArrayList<Category>()
    var catSelect : String = ""
    var productosSeleccionados = ArrayList<ItemOrder>()
    var precioTotal: Double = 0.0
}

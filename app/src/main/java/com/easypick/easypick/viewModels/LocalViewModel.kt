package com.easypick.easypick.viewModels

import androidx.lifecycle.ViewModel
import com.easypick.easypick.model.Category
import com.easypick.easypick.model.ItemOrder

class LocalViewModel: ViewModel() {
    var catSelect : Int = 0
    var nameCatSelect: String = ""
    var idStore: Long = 0
    var productosSeleccionados = ArrayList<ItemOrder>()
    var precioTotal: Double = 0.0
}

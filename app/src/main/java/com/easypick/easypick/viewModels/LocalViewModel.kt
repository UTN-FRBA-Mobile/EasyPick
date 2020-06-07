package com.easypick.easypick.viewModels

import androidx.lifecycle.ViewModel
import com.easypick.easypick.model.Producto

class LocalViewModel: ViewModel() {
    var categoria: String = ""
    var productosSeleccionados = ArrayList<Producto>()
    var precioTotal: Double = 0.0
}

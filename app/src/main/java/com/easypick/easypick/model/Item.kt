package com.easypick.easypick.model

class Item(val title: String, val description: String, val quantity: Int,
           val currency: String = "ARS", val unit_price: Double) {
}
package com.easypick.easypick.API

import com.easypick.easypick.model.Order
import com.google.firebase.firestore.FirebaseFirestore

class DatabaseAPI(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun addOrder(order: Order){
        db.collection("orders").add(order)
    }
}
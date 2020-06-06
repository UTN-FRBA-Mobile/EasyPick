package com.easypick.easypick.API

import com.easypick.easypick.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore


class DatabaseAPI(private val db: FirebaseFirestore = FirebaseFirestore.getInstance()) {
    fun addOrder(order: Order){
        db.collection("orders").add(order)
    }

    fun getOrder(id: String): Task<DocumentSnapshot> {
        return db.collection("orders").document(id).get()
    }

    fun updateToken(token: String){
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val data = hashMapOf("fcmToken" to token)
            db.collection("users").document(firebaseUser.uid).set(data)
        }
    }
}
package com.easypick.easypick.services

import android.app.IntentService
import android.content.Intent
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot

class UpdateOrderService(name: String="UpdateOrderService") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        val getOrder: Task<QuerySnapshot> = DatabaseAPI().getOrderById(
            intent?.extras?.get("orderId") as String)
        getOrder.addOnSuccessListener { orders ->
            for (dbOrder in orders){
                val order = dbOrder.toObject(Order::class.java)
                val updateIntent = Intent("UpdateOrderIntent");
                updateIntent.putExtra("orden", order)
                sendBroadcast(updateIntent)
            }
        }
    }
}
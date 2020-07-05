package com.easypick.easypick.model

import android.content.Context
import android.os.Parcelable
import com.easypick.easypick.R
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.text.SimpleDateFormat
import java.util.*

@Parcelize
data class Order(
    var payer: User? = null,
    val items: List<Item>? = null,
    val costo: Double = 0.0,
    var id: String = "0",
    val events: List<OrderEvent> =
                      listOf(OrderEvent("Orden creada",
                          SimpleDateFormat("dd/MM/yy K:m a ", Locale.ENGLISH).
                              format(Calendar.getInstance().time), OrderStatus.ACTIVE),
                          OrderEvent("Tu orden esta siendo preparada"),
                          OrderEvent("Tu orden estara lista en breve"),
                          OrderEvent("Orden lista para retirar"),
                          OrderEvent("Orden retirada")),
    val estimatedTime: Int = 15,
    val local: String = "",
    val completed: Boolean = false,
    @ServerTimestamp val timestamp:Date? = null) : Parcelable {
    fun statusColor(context: Context): Int {
        return if (this.completed){
            context.resources.getColor(R.color.orderComplete)
        } else {
            context.resources.getColor(R.color.button_color)
        }
    }
}
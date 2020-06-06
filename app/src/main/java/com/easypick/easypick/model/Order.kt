package com.easypick.easypick.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
data class Order(
    val payer: User? = null,
    val items: List<Item>? = null,
    val costo: Double = 0.0,
    var id: String = "0",
    val events: List<OrderEvent> =
                      listOf(OrderEvent("Orden creada",
                          DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Calendar.getInstance().time),
                          OrderStatus.ACTIVE),
                          OrderEvent("Tu orden esta siendo preparada"),
                          OrderEvent("Tu orden estara lista en breve"),
                          OrderEvent("Orden lista para retirar"),
                          OrderEvent("Orden retirada")),
    val estimatedTime: Int = 15,
    @ServerTimestamp val timestamp:Date? = null) :
    Parcelable
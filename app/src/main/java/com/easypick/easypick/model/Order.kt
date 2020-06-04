package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
data class Order (val payer: User, val items: List<Item>, val costo: Double, var id: String,
                  val events: List<OrderEvent> =
                      listOf(OrderEvent("Orden creada",
                          DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(Calendar.getInstance().time),
                          OrderStatus.ACTIVE),
                          OrderEvent("Tu orden esta siendo preparada"),
                          OrderEvent("Tu orden estara lista en breve"),
                          OrderEvent("Orden lista para retirar"),
                          OrderEvent("Orden retirada"))) :
    Parcelable
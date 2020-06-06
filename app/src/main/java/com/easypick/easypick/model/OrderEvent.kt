package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.text.DateFormat
import java.util.*

@Parcelize
class OrderEvent(
    var message: String = "Orden creada",
    var date: String = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(
        Calendar.getInstance().time),
    var status: OrderStatus = OrderStatus.INACTIVE
) : Parcelable
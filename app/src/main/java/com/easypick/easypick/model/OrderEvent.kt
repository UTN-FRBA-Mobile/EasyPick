package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class OrderEvent(
    var message: String,
    var date: String = "",
    var status: OrderStatus = OrderStatus.INACTIVE
) : Parcelable
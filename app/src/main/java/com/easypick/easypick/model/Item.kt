package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Item(val title: String, val description: String, val quantity: Int,
           val currency: String = "ARS", val unit_price: Double): Parcelable
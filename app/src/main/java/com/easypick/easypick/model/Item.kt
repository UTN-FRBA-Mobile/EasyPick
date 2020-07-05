package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Item(val title: String = "Item", val description: String = "Description",
           val quantity: Int = 0, val currency: String = "ARS",
           val unit_price: Double = 0.0,
           val imageURL: String = ""): Parcelable
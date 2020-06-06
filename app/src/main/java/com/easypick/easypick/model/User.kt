package com.easypick.easypick.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(val email: String = "test@gmail.com", val name: String = "Test",
                val id:String = "0"): Parcelable {
}
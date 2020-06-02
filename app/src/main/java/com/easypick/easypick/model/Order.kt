package com.easypick.easypick.model

import android.os.Parcelable
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.parcel.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Order (val payer: User, val items: List<Item>, val costo: Double, val id: Int) :
    Parcelable
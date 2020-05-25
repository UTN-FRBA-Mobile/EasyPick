package com.easypick.easypick.model

import com.google.firebase.auth.FirebaseUser

data class Order (val payer: User, val items: List<Item> ) {
}
package com.easypick.easypick.firebase

import android.content.Context
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.preferences.MyPreferences
import com.google.firebase.iid.FirebaseInstanceId

object FirebaseToken {
    fun storeToken(context: Context){
        var token = MyPreferences.getFirebaseToken(context)
        if (token == null) {
            FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener { tokenResult ->
                token = tokenResult.token;
                MyPreferences.setFirebaseToken(context, token!!)
            }
        }
        if (token != null){
            DatabaseAPI().updateToken(token!!)
        }
    }
}

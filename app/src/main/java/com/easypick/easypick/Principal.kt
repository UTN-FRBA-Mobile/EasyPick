package com.easypick.easypick

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.firebase.FirebaseToken
import com.easypick.easypick.fragments.ForceAuthFragment
import com.easypick.easypick.fragments.FragmentHome
import com.easypick.easypick.fragments.FragmentLocal
import com.easypick.easypick.fragments.ResumenOrdenFragment
import com.easypick.easypick.model.Order
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.easypick.easypick.fragments.*


class Principal :  BaseActivity(), FragmentHome.OnFragmentInteractionListener,
    ForceAuthFragment.OnFragmentInteractionListener, FragmentLocal.OnFragmentInteractionListener , FragmentProducto.OnFragmentInteractionListener, FragmentOrden.OnFragmentInteractionListener, FragmentOrdenEliminacion.OnFragmentInteractionListener  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras: Bundle? = intent?.extras
        if (extras != null) {
            if (extras.containsKey("fragment") and extras.containsKey("ordenId")){
                onNewIntent(intent)
            }
        }
        setContentView(R.layout.activity_principal)
        if (savedInstanceState == null) {
            showFragment(FragmentHome())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container_principal, fragment).addToBackStack(null)
            .commit()
    }

    override fun onStart() {
        super.onStart()
        FirebaseToken.storeToken(this.applicationContext)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val extras: Bundle? = intent?.extras
        if (extras != null){
            if (extras.containsKey("fragment") and extras.containsKey("ordenId")) {
                val getOrder: Task<DocumentSnapshot> = DatabaseAPI().getOrder(extras["ordenId"] as String)
                getOrder.addOnSuccessListener { documentSnapshot ->
                    val order = documentSnapshot.toObject(Order::class.java)
                    order?.let { ResumenOrdenFragment.newInstance(it) }?.let { showFragment(it) }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1){
            finish()
        }
        else{
            super.onBackPressed()
        }

    }

}


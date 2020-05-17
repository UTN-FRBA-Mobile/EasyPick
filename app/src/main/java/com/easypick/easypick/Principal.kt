package com.easypick.easypick

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.easypick.easypick.fragments.*
import kotlinx.android.synthetic.main.activity_principal.*


class Principal :  BaseActivity(), FragmentHome.OnFragmentInteractionListener,
    AuthFragment.OnFragmentInteractionListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        // replaceFragment(FragmentHome())
        btn_next.setOnClickListener { showFragment(PagoFragment()) }
        showFragment(FragmentHome())

    }

//    private fun replaceFragment(fragment: Fragment){
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frag_container_principal, fragment)
//        fragmentTransaction.addToBackStack(null)
//        fragmentTransaction.commit()
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.frag_container_principal, fragment)
            .commit()
    }

}


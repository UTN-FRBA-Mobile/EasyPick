package com.easypick.easypick

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.easypick.easypick.fragments.*


class Principal :  BaseActivity(), FragmentHome.OnFragmentInteractionListener,
    AuthFragment.OnFragmentInteractionListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        showFragment(FragmentHome())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?): Unit {
        super.onActivityResult(requestCode, resultCode, data)
        for (fragment in supportFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frag_container_principal, fragment)
            .commit()
    }

}


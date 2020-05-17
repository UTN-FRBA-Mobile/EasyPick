package com.easypick.easypick

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.replace
import com.easypick.easypick.fragments.FragmentHome
import com.easypick.easypick.fragments.FragmentPago
import com.easypick.easypick.fragments.PagoFragment
import com.easypick.easypick.fragments.FragmentMenu
import kotlinx.android.synthetic.main.activity_principal.*


class Principal :  AppCompatActivity()  {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)
        replaceFragment(FragmentHome())
        btn_next.setOnClickListener { replaceFragment(PagoFragment()) }

    }


    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frag_container_principal, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }





}


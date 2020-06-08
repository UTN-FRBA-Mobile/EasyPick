package com.easypick.easypick.fragments

import android.Manifest.*
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.lang.UCharacter
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION
import android.os.Bundle
import android.provider.MediaStore
import android.provider.SyncStateContract.Helpers.insert
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat.checkSelfPermission

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.Locales

import com.easypick.easypick.R.*

import com.easypick.easypick.adapters.AdaptadorLocales
import kotlinx.android.synthetic.main.activity_principal.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_producto.*
import java.security.Permission
import java.security.Permissions
import java.util.jar.Manifest


class FragmentHome : Fragment() {
    private var listener: OnFragmentInteractionListener? = null
    private val PERMISION_CODE:Int =1000
    private val IMAGE_CAPTURE_CODE:Int=1001
    var image_rui: Uri?= null


    private val locales = listOf(
    Locales(titulo = "Pizzeria", detalle = "Pizzeria Vegana, más de 14 sabores", foto = drawable.resto1) ,
    Locales(titulo = "Sarkis", detalle = "Comida armenia hecha por nosotros", foto = drawable.resto2) ,
    Locales(titulo = "Don Julio", detalle = "El mejor asado criollo de buenos aires", foto = drawable.resto3) ,
    Locales(titulo = "Zakura", detalle = "Comida japonesa, más de 8 platos orientales", foto = drawable.resto4),
    Locales(titulo = "Blur", detalle = "Cerveza Artenal, contamos con 14 tipos de cervezas.", foto = drawable.resto5),
    Locales(titulo = "MilaPlus", detalle = "Milanesas de lujo, carne, pollo, cerdo, cordero.", foto = drawable.resto6),
    Locales(titulo = "Cocu", detalle = "Panaderia francesa, los mejores panes y pastas de francia", foto = drawable.resto7)  )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        retainInstance = true
    }





    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout.fragment_home, container, false)

        // populate the views now that the layout has been inflated
        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            // RecyclerView node initialized here
            RecyLocales.apply {
                // set a LinearLayoutManager to handle Android
                // RecyclerView behavior
                layoutManager = LinearLayoutManager(activity)
                // set the custom adapter to the RecyclerView
                adapter = AdaptadorLocales(locales)
            }


          



        }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment)
    }



}



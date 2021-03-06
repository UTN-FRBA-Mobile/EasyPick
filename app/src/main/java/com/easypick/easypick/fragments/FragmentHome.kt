package com.easypick.easypick.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.Locales
import com.easypick.easypick.R
import com.easypick.easypick.R.drawable
import com.easypick.easypick.R.layout
import com.easypick.easypick.adapters.AdaptadorLocales
import com.easypick.easypick.viewModels.LocalViewModel
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.fragment_home.*
import me.dm7.barcodescanner.zxing.ZXingScannerView
import java.io.File


class FragmentHome : Fragment(){
    private var listener: OnFragmentInteractionListener? = null
    private var scannerView: ZXingScannerView?=null;
    private var REQUEST_CAMERA=1
    private var PERMISSION_CODE=1000
    var imageUri: Uri? = null
    var imageFile: File?= null
    private lateinit var viewModel: LocalViewModel

    private val locales = listOf(
        Locales(titulo = "Pizzeria", detalle = "Pizzeria Vegana, más de 14 sabores", foto= drawable.resto1,  id = 1) ,
        Locales(titulo = "Sarkis", detalle = "Comida armenia hecha por nosotros", foto = drawable.resto2, id = 2),
        Locales(titulo = "Don Julio", detalle = "El mejor asado criollo de buenos aires", foto = drawable.resto3, id = 3) ,
        Locales(titulo = "Zakura", detalle = "Comida japonesa, más de 8 platos orientales", foto = drawable.resto4, id = 4),
        Locales(titulo = "Blur", detalle = "Cerveza Artenal, contamos con 14 tipos de cervezas.", foto = drawable.resto5, id = 5),
        Locales(titulo = "MilaPlus", detalle = "Milanesas de lujo, carne, pollo, cerdo, cordero.", foto = drawable.resto6, id = 6))


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?
    ): View? = inflater.inflate(layout.fragment_home, container, false)

    // populate the views now that the layout has been inflated
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener?.selectTab("FragmentHome")
        context?.let { Glide.with(it).asGif().placeholder(R.drawable.qr_).
            load(R.drawable.qrscan).into(btn_cam) }
        // RecyclerView node initialized here
        RecyLocales.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)
            // set the custom adapter to the RecyclerView
            adapter = AdaptadorLocales(locales, object : ClickListener {
                override fun onCLick(vista: View, index: Int) {

                    var storeFrangment = FragmentLocal();

                    storeFrangment.store = locales.get(index);
                    viewModel.local = locales.get(index)
                    listener?.showFragment(storeFrangment, "verLocal")

                }
            })
        }
        //permission was not enabled
        val permission = arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        //show popup to request permission
        requestPermissions(permission, PERMISSION_CODE)



        btn_cam.setOnClickListener {

            val mScanner = IntentIntegrator(activity)
            mScanner.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
            mScanner.setBeepEnabled(false)
            mScanner.initiateScan()


        }

        //   print(apiResponse);
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "Scanned: " + result.contents, Toast.LENGTH_LONG).show()
                //aqui enviar al 2do slide

                var storeFrangment = FragmentLocal();
                // var localqr= Locales(titulo = "LocalQR", detalle = "QR",foto = drawable.resto1, id = result.contents.toLong())
                var qr= result.contents.split(",").toTypedArray()


                var localqr= Locales(titulo = qr[0].toString(), detalle = qr[1].toString(), foto = drawable.resto1, id= qr[2].toLong())
                storeFrangment.store =  localqr
                listener?.showFragment(storeFrangment, "verLocal")

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
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
        fun showFragment(fragment: Fragment, name: String)
        fun selectTab(fragmentName: String)
    }

}




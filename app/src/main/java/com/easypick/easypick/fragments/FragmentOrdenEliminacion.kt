package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.adapters.CarritoAdapter
import com.easypick.easypick.model.*
import com.easypick.easypick.viewModels.LocalViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_orden.*
import kotlin.math.round

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOrdenEliminacion.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOrdenEliminacion : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private var productosSeleccionados = ArrayList<ItemOrder>()
    private lateinit var viewModel: LocalViewModel
    var importeTotal: TextView?= null
    var bandera: Boolean = false
    var storeName: TextView?= null
    var storeDescription: TextView?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_orden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bandera = false
        listaProductosCarrito.apply {
            layoutManager = LinearLayoutManager(activity)
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)
            productosSeleccionados = viewModel.productosSeleccionados
            importeTotal = view.findViewById(R.id.precioTotal)
            storeName = view.findViewById(R.id.idStoreName)
            importeTotal?.text = viewModel.precioTotal.toString()
            storeName?.text = viewModel.tituloStore
            var importeApagar: Double = viewModel.precioTotal
            adapter = CarritoAdapter(productosSeleccionados, object : ClickListener{
                override fun onCLick(v: View, index: Int) {
                    bandera = true
                    importeApagar -= productosSeleccionados.get(index).precioUnitario
                    viewModel.precioTotal -= productosSeleccionados.get(index).precioUnitario
                    importeTotal?.text = viewModel.precioTotal.toString()
                    if(productosSeleccionados.get(index).cantidad >1){
                        productosSeleccionados.get(index).cantidad -= 1
                        productosSeleccionados.get(index).importe = productosSeleccionados.get(index).precioUnitario * productosSeleccionados.get(index).cantidad
                        Toast.makeText(activity, "Quedan ${productosSeleccionados.get(index).cantidad} de ${productosSeleccionados.get(index).description} en la orden", Toast.LENGTH_SHORT).show()
                    } else {
                        val i : ItemOrder
                        i = productosSeleccionados.get(index)
                        Toast.makeText(activity, "Se ha eliminado ${productosSeleccionados.get(index).description} de la orden", Toast.LENGTH_SHORT).show()
                        viewModel.productosSeleccionados.remove(i)
                    }
                    listener?.showFragment(FragmentOrdenEliminacion(), "")
                    if(productosSeleccionados.size == 0){
                        Toast.makeText(activity, "PEDIDO VACIO", Toast.LENGTH_LONG).show()
                    }
                }
            })

            generarOrden.setOnClickListener {
                bandera = true
                crearOrden()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(!bandera){
            val storeFragment = FragmentLocal()
            storeFragment.store = viewModel.local
            listener?.showFragment(storeFragment,"")
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentOrdenEliminacion.OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun crearOrden(){
        val items: ArrayList<Item> = ArrayList<Item>()
        for (producto: ItemOrder in productosSeleccionados){
            items.add(Item(title=producto.description, quantity=1, unit_price=producto.importe,
                imageURL = producto.image))
        }
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val user = firebaseUser?.email?.let {
            firebaseUser.displayName?.let { it1 -> User(it, it1, firebaseUser.uid) } }

        fun Double.round(decimals: Int): Double {
            var multiplier = 1.0
            repeat(decimals) { multiplier *= 10 }
            return round(this * multiplier) / multiplier
        }

        val order = Order(payer=user, items=items, costo=viewModel.precioTotal.round(2),
            local=viewModel.local.titulo)
        viewModel.precioTotal = 0.0
        viewModel.productosSeleccionados.clear()
        listener?.showFragment(ForceAuthFragment.newInstance(order), "intentoDePago")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOrdenEliminacion.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentOrdenEliminacion().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment, name: String)
        fun selectTab(fragmentName: String)
    }
}

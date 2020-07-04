package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
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

class FragmentOrden : Fragment() {
    private var listener: FragmentOrden.OnFragmentInteractionListener? = null
    private var productosSeleccionados = ArrayList<ItemOrder>()


    private lateinit var viewModel: LocalViewModel
    var importeTotal: TextView?= null



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
        listaProductosCarrito.apply {
            layoutManager = LinearLayoutManager(activity)
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)
            productosSeleccionados = viewModel.productosSeleccionados
            importeTotal = view.findViewById(R.id.precioTotal)
            importeTotal?.text = viewModel.precioTotal.toString()
            var importeApagar: Double = viewModel.precioTotal
            adapter = CarritoAdapter(productosSeleccionados, object : ClickListener{
                override fun onCLick(v: View, index: Int) {
                    importeApagar -= productosSeleccionados.get(index).precioUnitario
                    viewModel.precioTotal = importeApagar
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
                }
            })

            generarOrden.setOnClickListener {
                crearOrden()
            }
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentOrden.OnFragmentInteractionListener) {
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
            items.add(Item(title=producto.description, quantity=1, unit_price=producto.importe))
        }
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        val user = firebaseUser?.email?.let {
            firebaseUser.displayName?.let { it1 -> User(it, it1, firebaseUser.uid) } }
        val order = Order(payer=user, items=items, costo=viewModel.precioTotal)
        viewModel.precioTotal = 0.0
        viewModel.productosSeleccionados.clear()
        listener?.showFragment(ForceAuthFragment.newInstance(order), "intentoDePago")
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentOrden()
    }

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment, name: String)
    }

}

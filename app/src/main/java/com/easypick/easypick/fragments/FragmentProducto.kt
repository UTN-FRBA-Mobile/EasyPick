package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R


import com.easypick.easypick.adapters.ProductAdapter
import com.easypick.easypick.model.ItemOrder
import com.easypick.easypick.model.Producto
import com.easypick.easypick.viewModels.LocalViewModel
import kotlinx.android.synthetic.main.fragment_producto.*
import kotlinx.android.synthetic.main.template_producto.view.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentProducto.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentProducto : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var productos = ArrayList<Producto>()
    private lateinit var viewModel: LocalViewModel
    var categoria: TextView?= null
    private var catSeleccionada: String? = null
    private lateinit var fragmentProducto: Fragment
    private lateinit var fragmentOrden: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        listaProductos.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            fragmentOrden = FragmentOrden()
            fragmentProducto = FragmentProducto()
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)
            categoria = view.findViewById(R.id.categoria)
            categoria?.text = viewModel.categoria
            catSeleccionada = viewModel.categoria
            btn_carrito.setOnClickListener {
                listener?.showFragment(fragmentOrden)
            }
            var importeTotal: Double = viewModel.precioTotal
            productos = descargarproductos(catSeleccionada)
            adapter = ProductAdapter(productos, object :ClickListener{
                override fun onCLick(vista: View, index: Int) {
                    importeTotal += productos.get(index).precio
                    viewModel.precioTotal = importeTotal
                    var find: Boolean = false
                    if(viewModel.productosSeleccionados.size > 0) {
                        for (i in viewModel.productosSeleccionados) {
                            if (i.descripcion == productos.get(index).descripcion) {
                                find = true
                                i.importe += productos.get(index).precio
                                i.cantidad += 1
                            }
                        }
                    }
                    if(!find){
                        viewModel.productosSeleccionados.add((ItemOrder(productos.get(index).descripcion, productos.get(index).precio, productos.get(index).foto, productos.get(index).precio, 1)))
                     }

                    //viewModel.productosSeleccionados.add(Producto(productos.get(index).descripcion, productos.get(index).precio, productos.get(index).foto, productos.get(index).comentarios))
                    Toast.makeText(activity, "Se ha agregado ${productos.get(index).descripcion} al pedido", Toast.LENGTH_SHORT).show()
                }
            })
            /*btn_agregar.setOnClickListener{
                listener?.showFragment(fragmentOrden)
            }*/
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentProducto.OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun descargarproductos(cat: String?): ArrayList<Producto> {
        var cat = cat
        val prods = ArrayList<Producto>()
        if(cat == "Hamburguesas"){
            prods.add(Producto("Hamburguesa Completa", 300.0, R.drawable.hamburguesa, ""))
            prods.add(Producto("Hamburguesa LYT", 250.0, R.drawable.hamburguesa2, "" ))
            prods.add(Producto("Hamburguesa de Pollo", 200.0, R.drawable.hamburguesapollo, ""))
            prods.add(Producto("Hamburguesa con fritas", 350.0, R.drawable.hamb_fritas, ""))
            prods.add(Producto("Hamburguesa Gourmet", 350.0, R.drawable.hamb_gourmet, ""))
            prods.add(Producto("Hamburguesa con panceta", 300.0, R.drawable.hamb_panceta, ""))
            prods.add(Producto("Hamburguesa de Sushi", 450.0, R.drawable.hamb_sushi, ""))
        }else if(cat == "Ensaladas"){
            prods.add(Producto("Ensalada Cesar", 250.0, R.drawable.ensaladacesar, ""))
            prods.add(Producto("Ensalada de mar", 400.0, R.drawable.demar, ""))
            prods.add(Producto("Ensalada Primavera", 180.0, R.drawable.primavera, ""))
            prods.add(Producto("Ensalada cherry", 180.0, R.drawable.cherry, ""))
            prods.add(Producto("Ensalada con Pasta", 200.0, R.drawable.fideos, ""))
            prods.add(Producto("Ensalada Gourmet", 300.0, R.drawable.gourmet, ""))
        }else{
            prods.add(Producto("helado", 200.0, R.drawable.helado, ""))
            prods.add(Producto("Flan casero", 200.0, R.drawable.flan, ""))
            prods.add(Producto("Ensalada de fruta", 200.0, R.drawable.ensalada, ""))
            prods.add(Producto("Bombón Suizo", 250.0, R.drawable.bombon,""))
        }

        return prods
    }

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentProducto.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentProducto().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
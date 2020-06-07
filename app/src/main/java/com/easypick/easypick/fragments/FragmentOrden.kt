package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easypick.easypick.Interfaz.LongClickListener
import com.easypick.easypick.R
import com.easypick.easypick.adapters.CarritoAdapter
import com.easypick.easypick.model.Producto
import com.easypick.easypick.viewModels.LocalViewModel
import kotlinx.android.synthetic.main.fragment_orden.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentOrden.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentOrden : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var listener: FragmentOrden.OnFragmentInteractionListener? = null
    private var productosSeleccionados = ArrayList<Producto>()

    private lateinit var viewModel: LocalViewModel
    var importeTotal: TextView?= null
    //var isActionMode = false


    //private var adaptador: CarritoAdapter?= null

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
            adapter = CarritoAdapter(productosSeleccionados, object: LongClickListener{
                override fun longClick(vista: View, index: Int) {
                    importeApagar -= productosSeleccionados.get(index).precio
                    viewModel.precioTotal = importeApagar
                    importeTotal?.text = viewModel.precioTotal.toString()
                    val p : Producto
                    p = productosSeleccionados.get(index)
                    Toast.makeText(activity, "Se ha eliminado ${productosSeleccionados.get(index).descripcion} del pedido", Toast.LENGTH_SHORT).show()
                    viewModel.productosSeleccionados.remove(p)
                    listener?.showFragment(FragmentOrdenEliminacion())
                }
            } )
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
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentOrden.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentOrden().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment)
    }

}

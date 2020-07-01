package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
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


import com.easypick.easypick.adapters.ProductAdapter
import com.easypick.easypick.model.ItemOrder
import com.easypick.easypick.model.Producto
import com.easypick.easypick.retroFit.Gateway
import com.easypick.easypick.retroFit.RetroFitApiConsume
import com.easypick.easypick.viewModels.LocalViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_producto.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


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
    private lateinit var productos: List<Producto>
    private lateinit var viewModel: LocalViewModel
    var categoria: TextView?= null
    private var catSeleccionada: String? = null
    private lateinit var fragmentProducto: Fragment
    private lateinit var fragmentOrden: Fragment
    private var myCompositeDisposable: CompositeDisposable?= null

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
            //categoria?.text = viewModel.catSelect
            categoria?.text = "Categoria Seleccionada"
            btn_carrito.setOnClickListener {
                listener?.showFragment(fragmentOrden, "")
            }
            var importeTotal: Double = viewModel.precioTotal
           // productos = descargarproductos(catSeleccionada)

            val retroFitApiConsume = RetroFitApiConsume()
            val request = retroFitApiConsume.getRetrofit().create(Gateway::class.java)
            val call = request.getProductoByCategoryId(1, 2)


            call.enqueue(object : Callback<List<Producto>> {

                override fun onResponse(
                    call: Call<List<Producto>>,
                    response: Response<List<Producto>>
                ) {
                   if(response.isSuccessful){
                       productos = response.body()!!
                       Log.d("RESPONSE", productos.toString())

                       adapter = ProductAdapter(productos, object :ClickListener{
                           override fun onCLick(vista: View, index: Int) {
                               importeTotal += productos.get(index).price
                               viewModel.precioTotal = importeTotal
                               var find: Boolean = false
                               if(viewModel.productosSeleccionados.size > 0) {
                                   for (i in viewModel.productosSeleccionados) {
                                       if (i.Code == productos.get(index).Code) {
                                           find = true
                                           i.cantidad += 1
                                           i.importe = productos.get(index).price* i.cantidad
                                       }
                                   }
                               }
                               if(!find){
                                   viewModel.productosSeleccionados.add((ItemOrder(productos.get(index).Code, productos.get(index).description, productos.get(index).price, productos.get(index).image, 1, productos.get(index).price)))
                               }

                               //viewModel.productosSeleccionados.add(Producto(productos.get(index).descripcion, productos.get(index).precio, productos.get(index).foto, productos.get(index).comentarios))
                               Toast.makeText(activity, "Se ha agregado ${productos.get(index).description} al pedido", Toast.LENGTH_SHORT).show()
                           }
                       })
                   }
                }

                override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                    Toast.makeText(activity, "Error obteniendo productos", Toast.LENGTH_SHORT).show()
                }
            })
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

    /*private fun loadProduct(){
        val requestGateway = Retrofit.Builder()
            .baseUrl("https://gardinia.online/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(Gateway::class.java)
        myCompositeDisposable?.add(requestGateway.getProductoByCategoryId(1,2)
            .observeOn(AndroidSchedulers.mainThread()
    }*/


   /* fun descargarproductos(cat: String?): ArrayList<Producto> {
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
    }*/

    interface OnFragmentInteractionListener {
        fun showFragment(fragment: Fragment, name: String)
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





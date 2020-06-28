package com.easypick.easypick.fragments


import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.Locales
import com.easypick.easypick.R
import com.easypick.easypick.adapters.CategoryAdapter
import com.easypick.easypick.model.Category
import com.easypick.easypick.retroFit.Gateway
import com.easypick.easypick.retroFit.RetroFitApiConsume
import com.easypick.easypick.viewModels.LocalViewModel
import kotlinx.android.synthetic.main.fragment_local.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentLocal() : Fragment() {

    private var listener: FragmentLocal.OnFragmentInteractionListener? = null

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: LocalViewModel

    var flag: Boolean = false

    private lateinit var categories : List<Category>;

    public lateinit var store: Locales;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        flag = false
        // RecyclerView node initialized here
        recyclerViewCategories.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)

            val retroFitApiConsume = RetroFitApiConsume();
            val request = retroFitApiConsume.getRetrofit().create(Gateway::class.java);
            val call = request.getCategoryByStoreId(store.id);

            Handler().post {
                recyclerViewCategories.visibility = View.GONE
                loadingCategories.visibility = View.VISIBLE
            }

            call.enqueue(object : Callback<List<Category>> {
                override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                    if (response.isSuccessful) {
                        categories = response.body()!!

                        for (category in categories){
                            category.image = R.drawable.hamburguesa;
                        }

                        adapter = CategoryAdapter(categories, object : ClickListener {
                            override fun onCLick(vista: View, index: Int) {
                                flag = true
                                viewModel.categoria = categories?.get(index).name
                                listener?.showFragment(FragmentProducto(), "")
                            }
                        })

                        Handler().post {
                            recyclerViewCategories.visibility = View.VISIBLE
                            loadingCategories.visibility = View.GONE
                        }

                    }
                }


                override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                    Toast.makeText(activity, "Error obteniendo categorias", Toast.LENGTH_SHORT).show()
                }
            })

            initializeStore(view, store.titulo, store.detalle, store.foto);

        }
    }

    private fun initializeStore(view: View, name: String, description: String, image: Int) {
        val storeName: TextView? = view.findViewById(R.id.storeName)
        storeName?.text = name;

        val storeDescription: TextView? = view.findViewById(R.id.storeDescription)
        storeDescription?.text = description;

        val storeImage: ImageView? = view.findViewById(R.id.storeImage)
        storeImage?.setImageResource(image);
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentLocal.OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        if (!flag) {
            viewModel.precioTotal = 0.0
            viewModel.productosSeleccionados.clear()
            listener?.showFragment(FragmentHome(), "")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

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
         * @return A new instance of fragment FragmentLocal.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentLocal().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}

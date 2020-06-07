package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.easypick.easypick.Interfaz.ClickListener


import com.easypick.easypick.R
import com.easypick.easypick.adapters.CategoryAdapter
import com.easypick.easypick.model.Category
import com.easypick.easypick.viewModels.LocalViewModel
import kotlinx.android.synthetic.main.fragment_local.*
import java.util.ArrayList

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class FragmentLocal : Fragment() {

    private var listener: FragmentLocal.OnFragmentInteractionListener? = null
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var viewModel: LocalViewModel

    private val categoriesMock = listOf(
        Category("Ensaladas", "Las mas ricas de CABA"),
        Category("Hamburguesas", "Tipo americanas"),
        Category("Postres", "Para compartir")
    )


     //var categoria: TextView?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
        /*arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }*/


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_local, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // RecyclerView node initialized here
        recyclerViewCategories.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior
            layoutManager = LinearLayoutManager(activity)
            // set the custom adapter to the RecyclerView
            viewModel = ViewModelProvider(activity!!).get(LocalViewModel::class.java)
            adapter = CategoryAdapter(categoriesMock, object: ClickListener{
                override fun onCLick(vista: View, index: Int) {
                    viewModel.categoria = categoriesMock?.get(index).name
                    //categoria = activity?.findViewById(R.id.categoria)
                    //categoria?.text = categoriesMock?.get(index).name.toString()
                    //categoria?.text = categoriesMock?.get(index).name
                    listener?.showFragment(FragmentProducto())
                }
            })
                        initializeStore(view, "Burgertify", "Hamburguesas enserio");

            }
        }

        private fun initializeStore(view: View, name: String, description: String){
        var storeName: TextView? = view.findViewById(R.id.storeName)
        storeName?.text = name;

        var storeDescription: TextView? = view.findViewById(R.id.storeDescription)
        storeDescription?.text = description;
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentLocal.OnFragmentInteractionListener) {
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

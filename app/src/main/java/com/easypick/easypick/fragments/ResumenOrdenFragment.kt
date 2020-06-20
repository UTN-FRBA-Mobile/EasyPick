package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.R
import com.easypick.easypick.adapters.EstadoOrdenAdapter
import com.easypick.easypick.model.Order
import com.easypick.easypick.model.OrderEvent
import kotlinx.android.synthetic.main.fragment_resumen_orden.*
import kotlinx.android.synthetic.main.fragment_resumen_orden.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ResumenOrdenFragment: Fragment() {
    private lateinit var order: Order
    private var listener: FragmentHome.OnFragmentInteractionListener? = null
    private lateinit var mAdapter: EstadoOrdenAdapter
    private var mDataList = ArrayList<OrderEvent>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable("orden")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_resumen_orden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.costo.text = getString(R.string.costo, order.costo.toString())
        view.order_id.text = getString(R.string.orden_id, order.id)
        if (order.timestamp == null){
            view.fecha.text = DateFormat.getDateInstance(DateFormat.LONG,
                Locale("es", "ES")).format(Calendar.getInstance().time)
        }
        else{
            view.fecha.text = DateFormat.getDateInstance(DateFormat.LONG,
                Locale("es", "ES")).format(order.timestamp!!)
        }
        setDataListItems(order)
        initRecyclerView()
    }

    private fun setDataListItems(order: Order) {
        for (event: OrderEvent in order.events){
            mDataList.add(event)
        }
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter = EstadoOrdenAdapter(order.events)
        estadoOrden.layoutManager = mLayoutManager
        estadoOrden.adapter = mAdapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentHome.OnFragmentInteractionListener) {
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
    }

    companion object {
        @JvmStatic
        fun newInstance(orden: Order) =
            ResumenOrdenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("orden", orden)
                }
            }
        private const val TAG = "ResumenOrdenFragment"
    }

}
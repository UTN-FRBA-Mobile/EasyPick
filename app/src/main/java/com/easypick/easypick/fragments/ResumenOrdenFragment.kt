package com.easypick.easypick.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.Interfaz.OnBackPressedInterface
import com.easypick.easypick.R
import com.easypick.easypick.adapters.EstadoOrdenAdapter
import com.easypick.easypick.model.Order
import com.easypick.easypick.model.OrderEvent
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_resumen_orden.*
import kotlinx.android.synthetic.main.fragment_resumen_orden.view.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ResumenOrdenFragment: Fragment(), OnBackPressedInterface {
    private lateinit var order: Order
    private var listener: FragmentHome.OnFragmentInteractionListener? = null
    private lateinit var mAdapter: EstadoOrdenAdapter
    private var mDataList = ArrayList<OrderEvent>()
    private lateinit var mLayoutManager: LinearLayoutManager
    var comesFromPayment: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable("orden")!!
            comesFromPayment = it.getBoolean("comesFromPayment")
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
        view.estimado.text = getString(R.string.estimated,
            order.estimatedTime.toString())
        setDataListItems(order)
        initRecyclerView()
        swipeContainer.setOnRefreshListener {
            val getOrder: Task<QuerySnapshot> = DatabaseAPI().getOrderById(order.id)
            getOrder.addOnSuccessListener { orders ->
                var updatedOrder: Order? = null;
                for (dbOrder in orders) {
                    updatedOrder = dbOrder.toObject(Order::class.java)
                }
                if (updatedOrder != null){
                    updateOrderInformation(updatedOrder)
                    swipeContainer.isRefreshing = false;
                }
            }
        }
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

    private fun updateOrderInformation(orden: Order){
        estimado.text = "Tiempo estimado: " + orden.estimatedTime.toString() + " min"
        mAdapter = EstadoOrdenAdapter(orden.events)
        mAdapter.notifyDataSetChanged()
        Toast.makeText(context, "Order updated.", Toast.LENGTH_SHORT).show()
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
        fun newInstance(orden: Order, comesFromPayment: Boolean=false) =
            ResumenOrdenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("orden", orden)
                    putBoolean("comesFromPayment", comesFromPayment)
                }
            }
        private const val TAG = "ResumenOrdenFragment"
    }

    override fun popToTransactionName(): String {
        return if (comesFromPayment){
            "verLocal"
        } else{
            ""
        }
    }

}
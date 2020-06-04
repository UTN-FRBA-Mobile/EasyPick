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
import com.easypick.easypick.model.OrderStatus
import com.easypick.easypick.model.TimeLineModel
import kotlinx.android.synthetic.main.fragment_orden.*
import kotlinx.android.synthetic.main.fragment_orden.view.*

class OrdenFragment: Fragment() {
    private lateinit var order: Order
    private var listener: FragmentHome.OnFragmentInteractionListener? = null
    private lateinit var mAdapter: EstadoOrdenAdapter
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable("orden")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.costo.text = getString(R.string.costo, order.costo.toString())
        view.order_id.text = getString(R.string.orden_id, order.id)

        setDataListItems()
        initRecyclerView()
    }

    private fun setDataListItems() {
        mDataList.add(TimeLineModel("Orden retirada", "", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Orden lista para retirar", "", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Tu orden estara lista en breve", "2017-02-11 08:00", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Tu orden esta siendo preparada", "2017-02-10 15:00", OrderStatus.ACTIVE))
        mDataList.add(TimeLineModel("Orden creada", "2017-02-10 14:00", OrderStatus.COMPLETED))
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        mAdapter = EstadoOrdenAdapter(mDataList)
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
        fun showFragment(fragment: Fragment)
    }

    companion object {
        @JvmStatic
        fun newInstance(orden: Order) =
            OrdenFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("orden", orden)
                }
            }
        private const val TAG = "PagoFragment"
    }

}
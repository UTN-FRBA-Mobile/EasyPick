package com.easypick.easypick.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.R
import com.easypick.easypick.adapters.EstadoOrdenAdapter
import com.easypick.easypick.model.OrderStatus
import com.easypick.easypick.model.TimeLineModel
import kotlinx.android.synthetic.main.fragment_orden.*

class OrdenFragment: Fragment() {
    private lateinit var mAdapter: EstadoOrdenAdapter
    private val mDataList = ArrayList<TimeLineModel>()
    private lateinit var mLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_orden, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDataListItems()
        initRecyclerView()
    }

    private fun setDataListItems() {
        mDataList.add(TimeLineModel("Orden retirada", "", OrderStatus.INACTIVE))
        mDataList.add(TimeLineModel("Orden lista para retirar", "", OrderStatus.ACTIVE))
        mDataList.add(TimeLineModel("Tu orden estara lista en breve", "2017-02-11 08:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Procesamiento de la orden iniciado", "2017-02-10 15:00", OrderStatus.COMPLETED))
        mDataList.add(TimeLineModel("Orden creada", "2017-02-10 14:00", OrderStatus.COMPLETED))
    }

    private fun initRecyclerView() {
        mLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        estadoOrden.layoutManager = mLayoutManager
        mAdapter = EstadoOrdenAdapter(mDataList)
        estadoOrden.adapter = mAdapter
    }

}
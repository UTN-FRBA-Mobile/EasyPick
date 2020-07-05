package com.easypick.easypick.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.Interfaz.ClickListener
import com.easypick.easypick.R
import com.easypick.easypick.adapters.OrdersAdapter
import com.easypick.easypick.model.Order
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_order_history.*

class OrderHistoryFragment: Fragment() {
    private var listener: FragmentHome.OnFragmentInteractionListener? = null
    private var orders = ArrayList<Order>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_order_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersRecycler.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = OrdersAdapter(orders, context, object: ClickListener {
                override fun onCLick(vista: View, index: Int) {
                    val order = orders[index]
                    listener?.showFragment(ResumenOrdenFragment.newInstance(order), "")
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()
        ordersRecycler.addItemDecoration(MarginItemDecoration(
            resources.getDimension(R.dimen.default_padding).toInt()))
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null){
            Handler().post {
                ordersRecycler.visibility = View.GONE
                loadingOrders.visibility = View.VISIBLE
                noOrders.visibility = View.GONE
            }
            firebaseUser.uid.let { DatabaseAPI().getOrders(it) }
                .addOnSuccessListener { dbOrders ->
                    orders.clear()
                    for (dbOrder in dbOrders){
                        orders.add(dbOrder.toObject(Order::class.java))
                    }
                    if (orders.size != 0){
                        Handler().post {
                            loadingOrders.visibility = View.GONE
                            ordersRecycler.visibility = View.VISIBLE
                        }
                        listener?.selectTab("OrderHistoryFragment")
                    }
                    else{
                        showNoOrdersMessage()
                    }
                }
        }
        else {
            showNoOrdersMessage()
        }
    }

    private fun showNoOrdersMessage() {
        Handler().post {
            ordersRecycler.visibility = View.GONE
            noOrders.visibility = View.VISIBLE
        }
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
        fun selectTab(fragmentName: String)
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View,
                                parent: RecyclerView, state: RecyclerView.State) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left =  spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
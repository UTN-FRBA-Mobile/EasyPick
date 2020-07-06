package com.easypick.easypick.fragments

import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.easypick.easypick.API.DatabaseAPI
import com.easypick.easypick.Interfaz.OnBackPressedInterface
import com.easypick.easypick.R
import com.easypick.easypick.interfaces.MercadoPagoService
import com.easypick.easypick.model.Order
import com.mercadopago.android.px.core.MercadoPagoCheckout
import com.mercadopago.android.px.core.MercadoPagoCheckout.Builder
import com.mercadopago.android.px.model.exceptions.MercadoPagoError
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class PagoFragment : Fragment(), OnBackPressedInterface {
    private var listener: FragmentHome.OnFragmentInteractionListener? = null
    private lateinit var order: Order
    private val REQUEST_CODE = 1
    private val ACCESS_TOKEN = "TEST-4265074321617597-052423-80520673faae8bec8c7feb07b23ea749-84367971"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            order = it.getParcelable("orden")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pago, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listener?.selectTab("FragmentHome")
        order.id = UUID.randomUUID().toString().toUpperCase();
        val service = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.mercadopago.com/")
            .build()
            .create(MercadoPagoService::class.java)

        order.let { service.createPreferences(it, ACCESS_TOKEN) }.enqueue(object: Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                print("Error")
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val json = JSONObject(response.body()!!.string())
                val checkoutPreferenceId = json.getString("id")
                context?.let {
                    Builder(getString(R.string.mp_public_key), checkoutPreferenceId).build().startPayment(
                        it, REQUEST_CODE)
                }
            }
        })
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == MercadoPagoCheckout.PAYMENT_RESULT_CODE) {
                DatabaseAPI().addOrder(order)
                listener?.showFragment(ResumenOrdenFragment.newInstance(order, true), "")
            } else if (resultCode == RESULT_CANCELED) {
                if (data != null && data.extras != null && data.extras!!.containsKey(
                        MercadoPagoCheckout.EXTRA_ERROR)) {
                    val mercadoPagoError =
                        data.getSerializableExtra(MercadoPagoCheckout.EXTRA_ERROR) as MercadoPagoError
                    activity?.onBackPressed()
                } else {
                    // El usuario fue para atras / cancelo el pago
                    activity?.onBackPressed()
                }
            }
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

    companion object {
        @JvmStatic
        fun newInstance(orden: Order) =
            PagoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("orden", orden)
                }
            }
        private const val TAG = "PagoFragment"
    }

    override fun popToTransactionName(): String {
        return "intentoDePago"
    }
}

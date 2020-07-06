package com.easypick.easypick

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.easypick.easypick.adapters.AdaptadorLocales
import kotlinx.android.synthetic.main.activity_inicio.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.concurrent.thread
import kotlin.time.seconds


class MainActivity : AppCompatActivity(){

    var isStarted = false
    var progressStatus = 0
    var handler: Handler? = null
    var secondaryHandler: Handler? = Handler()
    var primaryProgressStatus = 0
    var secondaryProgressStatus:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)
        val btn_inicio = findViewById<Button>(R.id.btn_inicio)
        var barra = progressBar

        barra.visibility= View.GONE
       var barra2= progressBar2
        barra2.max=100

        barra2.isVisible=false

       //cuando cargo el button
        btn_inicio.setOnClickListener {
            barra.visibility=View.VISIBLE
            barra2.isVisible=true
            Thread(Runnable {
              var sum=1;
                while(sum<1000) {
                    barra2.progress = sum
                     sum++

                }
            }).start()

            val intent = Intent(this, Principal::class.java)
            startActivity(intent)
        }



    }



}

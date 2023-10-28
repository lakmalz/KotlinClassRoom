package com.example.kotlinclassroom

import android.net.http.HttpException
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import java.net.HttpRetryException
import java.net.HttpURLConnection

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var txt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.tvData)

// 1)        // This is the problem
//        lifecycleScope.launch {
//            try {
//                launch {
//                    throw Exception()
//                }
//            } catch (e: Exception) {
//                println("Caught exception: ${e.message}")
//            }
//        }

        // How to handle exception
        val handler = CoroutineExceptionHandler { ctx, throwable ->
            println("Caught exception: $throwable")
        }
        CoroutineScope(Dispatchers.Main + handler).launch {
            supervisorScope {
                launch {
                    delay(300L)
                    throw Exception("Coroutine 1 failed")
                }

                launch {
                    delay(400L)
                    println("Coroutine 2 finished")
                }
            }
        }

        // Job cancel and handle exception
        lifecycleScope.launch {
            val job = launch {
                try {
                    delay(500L)
                }catch (e: HttpRetryException){
                    if(e is CancellationException){
                        throw e
                    }
                    e.printStackTrace()
                }
                println("Coroutine 1 Finished")
            }

            delay(300L)
            job.cancel()
        // after cancel it is going to exception

        }
    }

}
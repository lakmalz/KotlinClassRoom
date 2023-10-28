package com.example.kotlinclassroom

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var txt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.tvData)

        val job = GlobalScope.launch(Dispatchers.Default) {
            repeat(5) {
                Log.d(TAG, "Coroutine is still working ...")
                delay(1000L)
            }
        }

        runBlocking {
            job.join()
            Log.d(TAG, "Main Thread is continuing")
        }

        /// Cancelation

//        val jobCancel = GlobalScope.launch(Dispatchers.Default) {
//            Log.d(TAG, "Starting long running Cal")
//            for (i in 30..40) {
//                if (isActive) {
//                    Log.d(TAG, "Result for i = $i : ${fib(i)}")
//                } else {
//                    Log.d(TAG, "isActive: $isActive")
//                }
//            }
//            Log.d(TAG, "Ending long running Cal")
//        }
//
//        runBlocking {
//            delay(2000L)
//            jobCancel.cancel()
//            Log.d(TAG, "Cancel the JobCancel")
//        }

        //Withtimeout
//        GlobalScope.launch(Dispatchers.Default) {
//            Log.d(TAG, "Starting long running Cal")
//            withTimeout(2000L) {
//                for (i in 30..40) {
//                    if (isActive) {
//                        Log.d(TAG, "Result for i = $i : ${fib(i)}")
//                    } else {
//                        Log.d(TAG, "isActive: $isActive")
//                    }
//                }
//            }
//            Log.d(TAG, "Ending long running Cal")
//        }

        //async
//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "Start running Cal")
//            val time = measureTimeMillis {
//                val answer1 = async { method1() }
//                val answer2 = async { method1() }
//                Log.d(TAG, "Method 1: ${answer1.await()}")
//                Log.d(TAG, "Method 2: ${answer2.await()}")
//            }
//            Log.d(TAG, "Execute Time: $time ms")
//        }
    }

    private suspend fun method1(): String {
        delay(3000L)
        return "Method 1"
    }

    private suspend fun method2(): String {
        delay(3000L)
        return "Method 2"
    }

    private fun fib(n: Int): Long {
        return if (n == 0) 0
        else if (n == 1) 1
        else fib(n - 1) + fib(n - 2)
    }
}
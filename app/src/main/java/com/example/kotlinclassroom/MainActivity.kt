package com.example.kotlinclassroom

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var txt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.tvData)
        // GlobalScope
        GlobalScope.launch {
            // This is not working after the application killed
            delay(5000L)
            Log.d(TAG, "Coroutine say Hello from Thread: ${Thread.currentThread().name}")
        }
        Log.d(TAG, "Hello from Thread: ${Thread.currentThread().name}")
        // End GlobalScope

        // Suspend Functions
        GlobalScope.launch {
            delay(1000L)
            Log.d(TAG, doNetworkCall())
            Log.d(TAG, doNetworkCall2())
            //THis should be declare inside the Coroutine function
        }
        // doNetworkCall()// We can not call suspend function from outside of Coroutines
        // End Suspend Functions

        // Coroutines context
        // Dispatchers.Main -> This is important for UI oprations
        // Dispatchers.IO -> this use all kinds of Data operations , Network call, DB, I/O files
        // Dispatchers.Default -> If you have COMPLEX & LONG RUNNING calculation
        // Dispatchers.Unconfined -> not confined to any specific thread
        GlobalScope.launch(Dispatchers.IO) {
            // first we get data from server
            val answer = doNetworkCall()

            // Now the answer need to update in UI therefore we use Dispatchers.Main
            withContext(Dispatchers.Main) {
                txt.text = answer
            }
        }
        // End Coroutines context


        //THis is only block main thread
        Log.d(TAG, "Before runBlocking Thread: ${Thread.currentThread().name}")
        runBlocking {
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO 1 : ${Thread.currentThread().name}")
            }
            launch(Dispatchers.IO) {
                delay(3000L)
                Log.d(TAG, "Finished IO 2 : ${Thread.currentThread().name}")
            }
            Log.d(TAG, "Start runBlocking Thread : ${Thread.currentThread().name}")
            delay(5000L)
            Log.d(TAG, "End runBlocking Thread : ${Thread.currentThread().name}")

        }
        Log.d(TAG, "After runBlocking Thread: ${Thread.currentThread().name}")

    }

    private suspend fun doNetworkCall(): String {
        delay(3000L)
        return "This is the answer1"
    }

    private suspend fun doNetworkCall2(): String {
        delay(3000L)
        return "This is the answer 2"
    }
}
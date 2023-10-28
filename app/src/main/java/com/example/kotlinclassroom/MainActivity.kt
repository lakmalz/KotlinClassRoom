package com.example.kotlinclassroom

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    private lateinit var txt: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.tvData)


        lifecycleScope.launch {
            try {
                launch {
                    throw Exception()
                }
            } catch (e: Exception) {
                println("Caught exception: ${e.message}")
            }
        }
    }

}
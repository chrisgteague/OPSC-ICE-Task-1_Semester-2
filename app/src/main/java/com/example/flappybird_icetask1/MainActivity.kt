package com.example.flappybird_icetask1

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    private lateinit var btnPlay: ImageButton
    private val Tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlay = findViewById(R.id.btnPlay)

        btnPlay.setOnClickListener{
            Log.d(Tag, "Button Play clicked")
        }
    }
}
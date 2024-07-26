package com.example.flappybird_icetask1

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import com.example.flappybird_icetask1.Model.ScreenSize
import com.example.flappybird_icetask1.UI.PlayGameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var btnPlay: ImageButton
    private val Tag = "MainActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ScreenSize.getScreenSize(this)

        btnPlay = findViewById(R.id.btnPlay)

        btnPlay.setOnClickListener{
            val iPlayGame = Intent(this@MainActivity,PlayGameActivity::class.java)
            startActivity(iPlayGame)
            finish()
            Log.d(Tag, "Button Play clicked")
        }
    }
}
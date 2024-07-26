package com.example.flappybird_icetask1.UI

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.flappybird_icetask1.R

class PlayGameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playView = PlayView(this)
        setContentView(playView)
    }
}
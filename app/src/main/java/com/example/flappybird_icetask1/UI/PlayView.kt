package com.example.flappybird_icetask1.UI

import android.content.Context
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.example.flappybird_icetask1.Thread.PlayThread

class PlayView(context: Context?) : SurfaceView(context) , SurfaceHolder.Callback {

    private val TAG = "PlayView"
    private var playThread : PlayThread? = null

    init {
        val holder = holder
        holder.addCallback(this)
        isFocusable = true
        playThread = PlayThread(holder,resources)
    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        TODO("Not yet implemented")
    }

}
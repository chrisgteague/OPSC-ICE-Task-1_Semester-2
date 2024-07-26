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


    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }
    override fun surfaceCreated(holder: SurfaceHolder) {
        if(!playThread!!.isRunning){
            playThread = holder.let {
                PlayThread(it!!, resources)
            }
        }else{
            playThread!!.start()
        }
    }
    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if(playThread!!.isRunning){
            playThread!!.isRunning = false
            var isCheck : Boolean = true
            while (isCheck) {
                try{
                    playThread!!.join()
                }catch (e : InterruptedException){

                }
            }
        }
    }

}
package com.example.flappybird_icetask1.Thread

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.flappybird_icetask1.Model.BackgroundImage
import com.example.flappybird_icetask1.Model.ScreenSize
import com.example.flappybird_icetask1.R

class PlayThread : Thread {

    private val TAG : String = "PlayThread"
    private var holder : SurfaceHolder
    private var resources : Resources
    var isRunning : Boolean = false //flag run or stop
        get() = field
        set(value) {
            field = value
        }
    private val FPS : Int = (1000.0/60.0).toInt() //time per frame for 60 FPS
    private val backgroundImage = BackgroundImage() //object model
    private var startTime : Long = 0
    private var frameTime : Long = 0
    private val velocity = 3

    constructor(holder: SurfaceHolder, resources: Resources){
        this.holder = holder
        this.resources = resources
        isRunning = true
    }

    override fun run(){
        Log.d(TAG, "Thread Started")
        while (isRunning){
            if (holder == null) return
            startTime = System.nanoTime()
            val canvas = holder.lockCanvas()
            if(canvas != null){
                try{
                    synchronized(holder){
                        render(canvas)
                    }
                }finally {
                    holder.unlockCanvasAndPost(canvas)
                }
            }
           frameTime = (System.nanoTime() - startTime)/1000000
            if (frameTime < FPS){
                try {
                    Thread.sleep(FPS - frameTime)
                }catch (e : InterruptedException){
                    Log.e("Interrupted", "Thread Sleep error")
                }
            }
        }
        Log.d(TAG, "Thread finish")
    }



    //render background
    private fun render(canvas: Canvas?) {
        Log.d(TAG, "Render canvas")
        var bitmapImage : Bitmap = BitmapFactory.decodeResource(resources, R.drawable.run_background)
        bitmapImage = ScaleResize(bitmapImage)

        backgroundImage.x = backgroundImage.x - velocity
        if(backgroundImage.x < -bitmapImage.width){
            backgroundImage.x = 0
        }

        canvas!!.drawBitmap(bitmapImage,(backgroundImage.x).toFloat(),(backgroundImage.y).toFloat(), null)

        //loop image
        if(backgroundImage.x < -bitmapImage.width - ScreenSize.SCREEN_WIDTH){
            canvas.drawBitmap(bitmapImage, (backgroundImage.x + bitmapImage.width).toFloat(),(backgroundImage.y).toFloat(), null)
        }
    }

    //resize image full screen
    private fun ScaleResize(bitmap: Bitmap): Bitmap {
        var ratio : Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth : Int = (ratio * ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap,scaleWidth,ScreenSize.SCREEN_HEIGHT, false)
    }
}
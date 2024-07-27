package com.example.flappybird_icetask1.Thread

import android.annotation.SuppressLint
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.Log
import android.view.SurfaceHolder
import com.example.flappybird_icetask1.Model.BackgroundImage
import com.example.flappybird_icetask1.Model.Bird
import com.example.flappybird_icetask1.Model.Cot
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
    private var bitmapImage : Bitmap? = null
    private var startTime : Long = 0
    private var frameTime : Long = 0
    private val velocity = 3
    private val bird : Bird  //bird model
    //state game : 0--> stop; 1--> running; 2 --> game over
    private var state : Int = 0
    private var velocityBird : Int = 0

    var cot : Cot? = null
    var numCot = 2

    constructor(holder: SurfaceHolder, resources: Resources){
        this.holder = holder
        this.resources = resources
        isRunning = true
        bird = Bird(resources)
        bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.run_background)
        bitmapImage = ScaleResize(bitmapImage!!)
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
                        RenderBird(canvas)
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

    private fun RenderBird(canvas: Canvas?) {
        if(state == 1){
            if(bird.y < (ScreenSize.SCREEN_HEIGHT - bird.getBird(0).height) || velocityBird < 0){
                velocityBird = velocityBird + 2
                bird.y = bird.y + velocityBird //up
            }
        }

        var current: Int = bird.currentFrame
        canvas!!.drawBitmap(bird.getBird(current), bird.x.toFloat(), bird.y.toFloat(), null)
        current++
        if (current > bird.maxFrame)  //current > 7
            current = 0
        bird.currentFrame = current

    }




    //render background
    private fun render(canvas: Canvas?) {
        Log.d(TAG, "Render canvas")
        backgroundImage.x = backgroundImage.x - velocity
        if(backgroundImage.x < -bitmapImage!!.width){
            backgroundImage.x = 0
        }

        bitmapImage?.let { canvas!!.drawBitmap(it,(backgroundImage.x).toFloat(),(backgroundImage.y).toFloat(), null) }

        //loop image
        if(backgroundImage.x < -(bitmapImage!!.width - ScreenSize.SCREEN_WIDTH)){
            bitmapImage?.let { canvas!!.drawBitmap(it, (backgroundImage.x + bitmapImage!!.width).toFloat(),(backgroundImage.y).toFloat(), null) }
        }
    }

    //resize image full screen
    private fun ScaleResize(bitmap: Bitmap): Bitmap {
        var ratio : Float = (bitmap.width / bitmap.height).toFloat()
        val scaleWidth : Int = (ratio * ScreenSize.SCREEN_HEIGHT).toInt()
        return Bitmap.createScaledBitmap(bitmap,scaleWidth,ScreenSize.SCREEN_HEIGHT, false)
    }


    @SuppressLint("SuspiciousIndentation")
    fun Jump() {
        state = 1

        if(bird.y > 0)
        velocityBird = -30
    }
}
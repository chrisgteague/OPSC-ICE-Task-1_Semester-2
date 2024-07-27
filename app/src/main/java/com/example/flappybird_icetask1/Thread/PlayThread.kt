package com.example.flappybird_icetask1.Thread

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.SurfaceHolder
import androidx.core.content.ContextCompat.startActivity
import com.example.flappybird_icetask1.MainActivity
import com.example.flappybird_icetask1.Model.BackgroundImage
import com.example.flappybird_icetask1.Model.Bird
import com.example.flappybird_icetask1.Model.BirdDie
import com.example.flappybird_icetask1.Model.Cot
import com.example.flappybird_icetask1.Model.ScreenSize
import com.example.flappybird_icetask1.R
import com.example.flappybird_icetask1.UI.PlayGameActivity
import kotlin.random.Random

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
    val numCot = 2
    val velocityCot = 10
    val minY = 250
    val maxY = ScreenSize.SCREEN_HEIGHT - minY - 500
    val kc = ScreenSize.SCREEN_WIDTH * 3/4
    var cotArray : ArrayList<Cot> = arrayListOf()
    var ran : Random = Random

    var iCot = 0
    var birdDie : BirdDie
    var isDie = false
    constructor(holder: SurfaceHolder, resources: Resources){
        this.holder = holder
        this.resources = resources
        isRunning = true
        bird = Bird(resources)
        bitmapImage = BitmapFactory.decodeResource(resources, R.drawable.run_background)
        bitmapImage = ScaleResize(bitmapImage!!)

        cot = Cot(resources)
        createCot(resources)

        birdDie = BirdDie(resources)


    }

    private fun createCot(resources: Resources) {
        for (i in 0 until numCot){
            val cot = Cot(resources)
            cot.x = ScreenSize.SCREEN_WIDTH + kc*i
            cot.ccY = ran.nextInt(maxY - minY) + minY
            cotArray.add(cot)
        }
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
                        //render background
                        render(canvas)

                        //render bird
                        renderBird(canvas)

                        //render cot
                        renderCot(canvas)

                        //render when death
                        renderDeath(canvas)
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

    private fun renderDeath(canvas: Canvas?) {
        if (isDie){
            var i : Int = birdDie.currentFrame
            canvas!!.drawBitmap(birdDie.getBirdDie(i), birdDie.x.toFloat(),birdDie.y.toFloat(), null)
            i++
            birdDie.currentFrame = i
            if(i == birdDie.maxFrame){
                isRunning = false

            }


        }
    }

    private fun renderCot(canvas: Canvas?) {
        if (state == 1){//running
            if(cotArray.get(iCot).x < bird.x - cot!!.w){
                iCot++
                if(iCot > numCot - 1){
                    iCot = 0
                }
            }
            else if (((cotArray.get(iCot).x)<bird.x + bird.getBird(0).width) &&
                (cotArray.get(iCot).ccY<bird.y || cotArray.get(iCot).getBottomY() < bird.y + bird.getBird(0).height)){
                isDie = true
            }
            for(i in 0 until numCot){
                if(cotArray.get(i).x < - cot!!.w){
                    cotArray.get(i).x = cotArray.get(i).x + numCot*kc
                    cotArray.get(i).ccY = ran.nextInt(maxY - minY) + minY
                }
                //move cot right --> left
                cotArray.get(i).x = cotArray.get(i).x - velocityCot
                //render cotTop
                canvas!!.drawBitmap(cot!!.cotTop,cotArray.get(i).x.toFloat(),cotArray.get(i).getTopY().toFloat(),null)
                //render cotBottom
                canvas!!.drawBitmap(cot!!.cotBottom,cotArray.get(i).x.toFloat(),cotArray.get(i).getBottomY().toFloat(),null)


            }
        }
    }

    private fun renderBird(canvas: Canvas?) {
        if(state == 1) {
            if (!isDie) {
                if (bird.y < (ScreenSize.SCREEN_HEIGHT - bird.getBird(0).height) || velocityBird < 0) {
                    velocityBird = velocityBird + 2
                    bird.y = bird.y + velocityBird //up
                }
            }
        }
        if (!isDie) {
            var current: Int = bird.currentFrame
            canvas!!.drawBitmap(bird.getBird(current), bird.x.toFloat(), bird.y.toFloat(), null)
            current++
            if (current > bird.maxFrame)
                current = 0
            bird.currentFrame = current
        }
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
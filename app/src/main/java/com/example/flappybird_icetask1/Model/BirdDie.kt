package com.example.flappybird_icetask1.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.flappybird_icetask1.R

class BirdDie(res : Resources) {
    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }
    var y : Int = 0
        get() = field
        set(value) {
            field = value
        }

    val maxFrame : Int = 3
    var currentFrame : Int = 0
        get() = field
        set(value) {
            field = value
        }

    var deathArray : ArrayList<Bitmap>

    init {
        deathArray = arrayListOf()
        deathArray.add(BitmapFactory.decodeResource(res, R.drawable.youdied_0))
        deathArray.add(BitmapFactory.decodeResource(res, R.drawable.youdied_1))
        deathArray.add(BitmapFactory.decodeResource(res, R.drawable.youdied_2))
        deathArray.add(BitmapFactory.decodeResource(res, R.drawable.youdied_3))

        //x = ScreenSize.SCREEN_WIDTH/2 - birdArray[0].width/2
        //y = ScreenSize.SCREEN_HEIGHT/2 - birdArray[0].height/2
    }
    fun getBirdDie(i : Int) : Bitmap{
        return deathArray.get(i)
    }
}
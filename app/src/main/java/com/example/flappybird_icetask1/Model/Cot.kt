package com.example.flappybird_icetask1.Model

import android.content.res.Resources
import android.graphics.BitmapFactory
import com.example.flappybird_icetask1.R

class Cot(res : Resources) {
    val cotTop = BitmapFactory.decodeResource(res, R.drawable.cot_top)
    val cotBottom = BitmapFactory.decodeResource(res, R.drawable.cot_bottom)

    val w = cotTop.width
    val h = cotTop.height

    var x : Int = 0
        get() = field
        set(value) {
            field = value
        }

    var ccY : Int = 0
        get() = field
        set(value) {
            field = value
        }

    fun getTopY() : Int{
        return ccY - h
    }
    fun getBottomY() : Int{
        return ccY + 500
    }
}
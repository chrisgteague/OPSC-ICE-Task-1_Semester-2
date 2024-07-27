package com.example.flappybird_icetask1.Model

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.flappybird_icetask1.R

class Bird (res : Resources){
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
  var birdArray : ArrayList<Bitmap>

  init {
      birdArray = arrayListOf()
      birdArray.add(BitmapFactory.decodeResource(res, R.drawable.flappy_1))
      birdArray.add(BitmapFactory.decodeResource(res, R.drawable.flappy_2))
      birdArray.add(BitmapFactory.decodeResource(res, R.drawable.flappy_3))
      birdArray.add(BitmapFactory.decodeResource(res, R.drawable.flappy_4))

      x = ScreenSize.SCREEN_WIDTH/2 - birdArray[0].width/2
      y = ScreenSize.SCREEN_HEIGHT/2 - birdArray[0].height/2
  }

  fun getBird(current : Int): Bitmap{
    return birdArray.get(current)
  }

}
package com.andrew.chopik.camera.utills

import android.content.Context
import android.view.OrientationEventListener
import com.andrew.chopik.camera.ORIENTATION_LANDSCAPE
import com.andrew.chopik.camera.ORIENTATION_PORTRAIT
import com.andrew.chopik.camera.ORIENTATION_REVERSE_LANDSCAPE
import com.andrew.chopik.camera.ORIENTATION_REVERSE_PORTRAIT

class OrientationListener(val context: Context) : OrientationEventListener(context) {

    var orientation = 0

    override fun onOrientationChanged(orientation: Int) {
       this.orientation = orientation
    }

    fun deviceOrientation() = when(orientation){
        in 0..45, in 316..360 -> ORIENTATION_PORTRAIT
        in 46..135 -> ORIENTATION_LANDSCAPE
        in 136..225 -> ORIENTATION_REVERSE_PORTRAIT
        in 226..315 -> ORIENTATION_REVERSE_LANDSCAPE
        else -> 0
    }
}
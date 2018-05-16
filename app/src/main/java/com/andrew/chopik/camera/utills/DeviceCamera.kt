package com.andrew.chopik.camera.utills

import android.view.View

interface DeviceCamera {

    fun setUpCamera()

    fun releaseCamera()

    fun takePicture(captureListener: CaptureListener, deviceRotation: Int)

    fun switchFlash(): Int

    fun switchCamera()

    fun getPreview(): View
}
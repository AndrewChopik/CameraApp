package com.andrew.chopik.camera.utills

import android.view.SurfaceHolder
import android.view.SurfaceView
import android.content.ContentValues.TAG
import android.content.Context
import android.hardware.Camera
import android.util.AttributeSet
import android.util.Log
import java.io.IOException


/**
 * Created by Andrew on 04.05.2018.
 */
class CameraPreview @JvmOverloads constructor(context: Context,
                                              camera: Camera,
                                              attrs: AttributeSet? = null,
                                              defStyleAttr: Int = 0)
    : SurfaceView(context, attrs, defStyleAttr), SurfaceHolder.Callback {

    private var mHolder: SurfaceHolder
    private var mCamera: Camera = camera

    init {
        mHolder = holder
        mHolder.addCallback(this)
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            mCamera.setPreviewDisplay(holder)
            mCamera.startPreview()
        } catch (e: IOException) {
            Log.d(TAG, "Error setting camera preview")
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, w: Int, h: Int) {
        try {
            mCamera.setPreviewDisplay(mHolder)
            mCamera.startPreview()

        } catch (e: Exception) {
            Log.d(TAG, "Error starting camera preview: " + e.message)
        }
    }
}
package com.andrew.chopik.camera.utills

import android.content.Context
import android.hardware.Camera
import android.view.View
import com.andrew.chopik.camera.FLASH_MODE_AUTO
import com.andrew.chopik.camera.FLASH_MODE_OFF
import com.andrew.chopik.camera.FLASH_MODE_ON

class DeviceCameraImpl(val context: Context) : DeviceCamera {

    var camera1: Camera? = null
    lateinit var preview: CameraPreview
    var isBackCameraActive = true

    val flashMode = arrayListOf(FLASH_MODE_AUTO, FLASH_MODE_ON, FLASH_MODE_OFF)
    var activeFlashMode = flashMode[0]

    override fun setUpCamera() {
        if (Camera.getNumberOfCameras() >= 1){
            if (isBackCameraActive){
                camera1 = getBackCamera()
            } else {
                camera1 = getFrontCamera()
            }
        }
        camera1?.let { preview = CameraPreview(context, it) }
    }

    override fun releaseCamera() {
        camera1?.release()
    }

    override fun getPreview(): View {
        return preview
    }

    override fun takePicture(captureListener: CaptureListener, deviceRotation: Int) {
        val pictureCallback = Camera.PictureCallback { bytes: ByteArray, camera: Camera ->
            captureListener.onCapture(bytes)
            camera.startPreview()
        }
        val params = camera1?.parameters
        params?.setRotation(deviceRotation)
        camera1?.parameters = params
        camera1?.takePicture(null, null, pictureCallback)
    }

    override fun switchFlash(): Int {
        releaseCamera()
        activeFlashMode = flashMode[activeFlashMode % flashMode.size]
        setUpCamera()
        return activeFlashMode
    }

    override fun switchCamera() {
        releaseCamera()
        isBackCameraActive = isBackCameraActive.not()
        setUpCamera()
    }

    private fun getBackCamera() = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK).apply {
        val params = this?.parameters
        params?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
        params?.flashMode = getFlashMode()
        this.parameters = params
    }

    private fun getFrontCamera()= Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT)

    private fun getFlashMode() = when (activeFlashMode){
        FLASH_MODE_AUTO -> Camera.Parameters.FLASH_MODE_AUTO
        FLASH_MODE_ON -> Camera.Parameters.FLASH_MODE_ON
        FLASH_MODE_OFF -> Camera.Parameters.FLASH_MODE_OFF
        else -> Camera.Parameters.FLASH_MODE_AUTO
    }

}
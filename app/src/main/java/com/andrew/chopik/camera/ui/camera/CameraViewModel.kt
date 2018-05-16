package com.andrew.chopik.camera.ui.camera

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.andrew.chopik.camera.FLASH_MODE_AUTO
import com.andrew.chopik.camera.data.CameraRepository
import com.andrew.chopik.camera.router.Router
import com.andrew.chopik.camera.utills.CaptureListener
import com.andrew.chopik.camera.utills.DeviceCamera

class CameraViewModel(val repository: CameraRepository, val deviceCamera: DeviceCamera, val router: Router) : ViewModel() {

    private var camera: MutableLiveData<DeviceCamera> = MutableLiveData()
    fun camera(): LiveData<DeviceCamera> = camera
    private var flashMode: MutableLiveData<Int> = MutableLiveData()
    fun flashMode(): LiveData<Int> = flashMode

    fun setUpCamera(){
        deviceCamera.setUpCamera()
        camera.value = deviceCamera
        flashMode.value = FLASH_MODE_AUTO
    }

    fun releaseCamera(){
        deviceCamera.releaseCamera()
    }

    fun takePicture(deviceRotation: Int){
        deviceCamera.takePicture(object : CaptureListener {
            override fun onCapture(byteArray: ByteArray) {
                savePicture(byteArray)
            }
        }, deviceRotation)
    }

    private fun savePicture(byteArray: ByteArray){
        repository.insertData(byteArray)
    }

    fun switchCamera(){
        deviceCamera.switchCamera()
        camera.value = deviceCamera
    }

    fun switchFlash(){
        flashMode.value = deviceCamera.switchFlash()
        camera.value = deviceCamera
    }

    fun openGallery(){
        router.openGalleryScreen()
    }
}
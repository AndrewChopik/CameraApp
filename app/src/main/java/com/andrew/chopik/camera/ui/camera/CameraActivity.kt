package com.andrew.chopik.camera.ui.camera

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.andrew.chopik.camera.FLASH_MODE_AUTO
import com.andrew.chopik.camera.FLASH_MODE_OFF
import com.andrew.chopik.camera.FLASH_MODE_ON
import com.andrew.chopik.camera.R
import com.andrew.chopik.camera.utills.DeviceCamera
import com.andrew.chopik.camera.utills.OrientationListener
import kotlinx.android.synthetic.main.activity_camera.*
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.android.inject
import permissions.dispatcher.*

@RuntimePermissions
class CameraActivity : AppCompatActivity() {

    val viewModel: CameraViewModel by viewModel()
    val orientationListener: OrientationListener by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        buttonCapture.setOnClickListener { viewModel.takePicture(orientationListener.deviceOrientation()) }
        buttonSwitchCamera.setOnClickListener { viewModel.switchCamera() }
        buttonFlash.setOnClickListener { viewModel.switchFlash() }
        buttonGallery.setOnClickListener { viewModel.openGallery() }
    }

    override fun onResume() {
        super.onResume()
        orientationListener.enable()
        setUpViewWithPermissionCheck()
    }

    override fun onPause() {
        super.onPause()
        orientationListener.disable()
        viewModel.releaseCamera()
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun setUpView() {
        viewModel.setUpCamera()
        viewModel.camera().observe(this, Observer<DeviceCamera> {
            cameraPreview.removeAllViews()
            cameraPreview.addView(it?.getPreview())
        })
        viewModel.flashMode().observe(this, Observer<Int> {
            when (it){
                FLASH_MODE_AUTO -> buttonFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_auto))
                FLASH_MODE_ON -> buttonFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_on))
                FLASH_MODE_OFF -> buttonFlash.setImageDrawable(resources.getDrawable(R.drawable.ic_flash_off))
            }
        })
    }

    @SuppressLint("NeedOnRequestPermissionsResult")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        onRequestPermissionsResult(requestCode, grantResults)
    }
}

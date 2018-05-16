package com.andrew.chopik.camera.di

import com.andrew.chopik.camera.data.CameraRepository
import com.andrew.chopik.camera.data.DataStorage
import com.andrew.chopik.camera.data.InternalStorage
import com.andrew.chopik.camera.router.Router
import com.andrew.chopik.camera.router.RouterImpl
import com.andrew.chopik.camera.ui.camera.CameraViewModel
import com.andrew.chopik.camera.ui.detail.DetailViewModel
import com.andrew.chopik.camera.ui.gallery.GalleryViewModel
import com.andrew.chopik.camera.utills.DeviceCamera
import com.andrew.chopik.camera.utills.DeviceCameraImpl
import com.andrew.chopik.camera.utills.OrientationListener
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.face.FaceDetector
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

val cameraModule = applicationContext {
    viewModel { CameraViewModel(get(), get(), get()) }
    viewModel { DetailViewModel(get(), get()) }
    viewModel { GalleryViewModel(get(), get()) }
    bean { RouterImpl(get()) as Router }
    bean { InternalStorage(get()) as DataStorage }
    bean { CameraRepository(get()) }
    bean { DeviceCameraImpl(get()) as DeviceCamera }
    bean { OrientationListener(get()) }
    bean { FaceDetector.Builder(get()).setTrackingEnabled(false).build() }
    bean { BarcodeDetector.Builder(get())
            .setBarcodeFormats(Barcode.DATA_MATRIX or Barcode.QR_CODE)
            .build() }
}
package com.andrew.chopik.camera.router

import com.andrew.chopik.camera.data.Photo

interface Router {

    fun openCameraScreen()

    fun openGalleryScreen()

    fun openDetailScreen(photo: Photo)
}
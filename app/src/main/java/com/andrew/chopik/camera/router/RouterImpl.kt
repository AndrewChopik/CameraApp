package com.andrew.chopik.camera.router

import android.content.Context
import android.content.Intent
import com.andrew.chopik.camera.EXTRA_URL
import com.andrew.chopik.camera.data.Photo
import com.andrew.chopik.camera.ui.camera.CameraActivity
import com.andrew.chopik.camera.ui.detail.DetailActivity
import com.andrew.chopik.camera.ui.gallery.GalleryActivity

class RouterImpl(val context: Context) : Router {

    override fun openCameraScreen() {
        context.startActivity(Intent(context, CameraActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun openGalleryScreen() {
        context.startActivity(Intent(context, GalleryActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }

    override fun openDetailScreen(photo: Photo) {
        context.startActivity(Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_URL, photo.url)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        })
    }
}
package com.andrew.chopik.camera.ui.gallery

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.andrew.chopik.camera.data.CameraRepository
import com.andrew.chopik.camera.data.Photo
import com.andrew.chopik.camera.router.Router

class GalleryViewModel(val repository: CameraRepository, val router: Router) : ViewModel() {

    private var photos: MutableLiveData<List<Photo>> = MutableLiveData()
    fun photos(): LiveData<List<Photo>> = photos

    fun loadPhotos(){
        photos.value = repository.loadPhotos()
    }

    fun openDetail(photo: Photo){
        router.openDetailScreen(photo)
    }
}
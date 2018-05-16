package com.andrew.chopik.camera.data

class CameraRepository(val storage: DataStorage) {

    fun insertData(byteArray: ByteArray){
        storage.insertData(byteArray)
    }

    fun loadPhotos() = storage.loadData()
}
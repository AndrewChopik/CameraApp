package com.andrew.chopik.camera.data

interface DataStorage {

    fun loadData(): List<Photo>

    fun insertData(byteArray: ByteArray)
}
package com.andrew.chopik.camera.data

import android.content.Context
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class InternalStorage(val context: Context) : DataStorage {

    override fun loadData(): List<Photo> {
        val file = File(context.filesDir, "CameraPictures")

        val list = file.list()
        val photoList: MutableList<Photo> = mutableListOf()

        if(list != null && list.isNotEmpty()){
            for(i in list){
                photoList.add(Photo(file.absolutePath + File.separatorChar + i))
            }
        }
        return photoList
    }

    override fun insertData(byteArray: ByteArray) {
        val file = File(context.filesDir, "CameraPictures")

        if (!file.exists()) {
            file.mkdirs()
            Log.e("TAG", "Directory not created")
        }

        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Date())
        val mediaFile = File(file.absolutePath + File.separator
                + "IMG_" + timeStamp + ".jpg")
        mediaFile.createNewFile()

        try {
            val fos = FileOutputStream(mediaFile)
            fos.write(byteArray)
            fos.close()
        } catch (e: FileNotFoundException) {
            Log.d("TAG", "File not found: " + e.message)
        } catch (e: IOException) {
            Log.d("TAG", "Error accessing file:")
        }
    }
}
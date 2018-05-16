package com.andrew.chopik.camera.ui.detail

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.graphics.*
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.barcode.Barcode
import com.google.android.gms.vision.barcode.BarcodeDetector
import com.google.android.gms.vision.face.FaceDetector

class DetailViewModel(val barcodeDetector: BarcodeDetector, val faceDetector: FaceDetector) : ViewModel() {

    private var bitmap: MutableLiveData<Bitmap> = MutableLiveData()
    fun bitmap(): LiveData<Bitmap> = bitmap
    private var barcode: MutableLiveData<Barcode> = MutableLiveData()
    fun barcode(): LiveData<Barcode> = barcode

    fun setBitmap(bitmap: Bitmap?) {
        this.bitmap.value = bitmap
    }

    fun scanFaces() {
        val myRectPaint = Paint()
        myRectPaint.setStrokeWidth(5f)
        myRectPaint.setColor(Color.RED)
        myRectPaint.setStyle(Paint.Style.STROKE)

        val tempBitmap = Bitmap.createBitmap(bitmap.value?.width ?: 0, bitmap.value?.height
                ?: 0, Bitmap.Config.RGB_565)
        val tempCanvas = Canvas(tempBitmap)
        tempCanvas.drawBitmap(bitmap.value, 0f, 0f, null)

        if (!faceDetector.isOperational()) {

        }

        val frame = Frame.Builder().setBitmap(bitmap.value).build()
        val faces = faceDetector.detect(frame)

        for (i in 0 until faces.size()) {
            val thisFace = faces.valueAt(i)
            val x1 = thisFace.position.x
            val y1 = thisFace.position.y
            val x2 = x1 + thisFace.width
            val y2 = y1 + thisFace.height
            tempCanvas.drawRoundRect(RectF(x1, y1, x2, y2), 2f, 2f, myRectPaint)
        }
        bitmap.value = tempBitmap
    }

    fun scanBarcode() {
        val frame = Frame.Builder().setBitmap(bitmap.value).build()
        val barcodes = barcodeDetector.detect(frame)

        if (barcodes.size() > 0){
            barcode.value = barcodes.valueAt(0)
        }
    }
}
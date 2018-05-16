package com.andrew.chopik.camera.ui.detail

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.andrew.chopik.camera.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_detail.*
import android.graphics.drawable.BitmapDrawable
import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.google.android.gms.vision.barcode.Barcode
import com.andrew.chopik.camera.EXTRA_BARCODE
import com.andrew.chopik.camera.EXTRA_URL
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.koin.android.architecture.ext.viewModel

class DetailActivity : AppCompatActivity() {

    val viewModel: DetailViewModel by viewModel()

    var bit: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbarDetail as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra(EXTRA_URL)

        Glide.with(this)
                .asBitmap()
                .load(url)
                .listener(object : RequestListener<Bitmap>{
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean {
                        return false
                    }

                    override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        viewModel.setBitmap(resource)
                        return false
                    }
                })
                .into(photoDetail)

        viewModel.bitmap().observe(this, Observer<Bitmap> {
            photoDetail.setImageDrawable(BitmapDrawable(resources, it))
        })
        viewModel.barcode().observe(this, Observer<Barcode> {
            val dialog = BarcodeDialog()
            dialog.arguments = Bundle().apply {
                putString(EXTRA_BARCODE, it?.rawValue)
            }
            dialog.show(supportFragmentManager, "BarcodeDialog")
        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menuInflater.inflate(R.menu.detail_activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuItemFace -> { viewModel.scanFaces() }
            R.id.menuItemBarcode ->  viewModel.scanBarcode()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}

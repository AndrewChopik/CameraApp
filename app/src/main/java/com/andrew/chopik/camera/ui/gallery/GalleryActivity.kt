package com.andrew.chopik.camera.ui.gallery

import android.arch.lifecycle.Observer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.Toolbar
import com.andrew.chopik.camera.R
import com.andrew.chopik.camera.data.Photo
import kotlinx.android.synthetic.main.activity_gallery.*
import org.koin.android.architecture.ext.viewModel

class GalleryActivity : AppCompatActivity() {

    val viewModel: GalleryViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        setSupportActionBar(toolbarGallery as Toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.loadPhotos()

        val adapter = GalleryListAdapter(emptyList(), object : GalleryListAdapter.ItemClickListener {
            override fun onItemClicked(viewHolder: GalleryListAdapter.ViewHolder) {
                val ph = viewModel.photos().value?.get(viewHolder.adapterPosition) ?: Photo("url")
                viewModel.openDetail(ph)
            }
        })

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        viewModel.photos().observe(this, Observer<List<Photo>> {
            adapter.setNotes(it ?: emptyList())
        })
    }
}

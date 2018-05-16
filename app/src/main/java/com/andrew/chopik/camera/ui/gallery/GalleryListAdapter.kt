package com.andrew.chopik.camera.ui.gallery

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.andrew.chopik.camera.R
import com.andrew.chopik.camera.data.Photo
import com.bumptech.glide.Glide

class GalleryListAdapter(private var photos: List<Photo> = emptyList(),
                         private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<GalleryListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View, itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView) {

        private val itemPhoto: ImageView = itemView.findViewById(R.id.itemPhotoImageView)

        init {
            itemView.setOnClickListener { itemClickListener.onItemClicked(this) }
        }

        fun bind(photo: Photo) {
            Glide.with(itemView.context)
                    .load(photo.url)
                    .into(itemPhoto)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.gallery_item, parent, false)

        return ViewHolder(v, itemClickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount() = photos.size

    fun setNotes(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    interface ItemClickListener {

        fun onItemClicked(viewHolder: ViewHolder)
    }
}
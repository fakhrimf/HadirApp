package com.hadir.hadirapp.utils

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

//Put Your Binding Adapters Here
@BindingAdapter("loadImage", "error")
fun loadImage(imageView: ImageView, resUrl: String?, error: Drawable) {
    Picasso.get().load(resUrl).error(error).into(imageView)
}
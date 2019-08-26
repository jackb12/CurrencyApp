package com.example.currencyapp.glide

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.currencyapp.R

@GlideModule
class GlideModule : AppGlideModule() {

    companion object {
        fun loadImage(context: Context, view: ImageView, url: String) {
            Glide
                .with(context)
                .`as`(PictureDrawable::class.java)
                .load(Uri.parse(url))
                .placeholder(R.color.textColourGrey)
                .into(view)
        }


        fun loadImage(context: Context, view: ImageView, drawable: Drawable) {
            Glide
                .with(context)
                .load(drawable)
                .placeholder(R.color.textColourGrey)
                .into(view)
        }
    }
}
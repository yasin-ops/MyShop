package com.example.myshopeeukotlin.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myshopeeukotlin.R
import java.io.IOException

class GlideLoader(val context:Context) {
    //imageUri:Uri
    fun loadUserPicture(imageUri:Uri,imageView: ImageView){
        try{
            // Load the User image in the ImageView
            Glide
                .with(context)
                .load(Uri.parse(imageUri.toString()))
                .centerCrop()
                .placeholder(R.drawable.shop )
                .into(imageView)
        }
        catch (e:IOException){
            e.printStackTrace()
        }
    }
}
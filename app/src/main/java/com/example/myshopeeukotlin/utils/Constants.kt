package com.example.myshopeeukotlin.utils

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.webkit.MimeTypeMap
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat

object Constants {

    const val USERS: String = "users"

    // Start
    // Using SharedPreference To Store limited amount of Data
    const val MYSHOPPAL_PREFERENCES: String = "MyShopPalPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"

    //End
    const val EXTRA_USER_DETAILS: String = "extra_user_detail"

    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE: String = "male"
    const val FEMALE: String = "female"

    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val USER_PROFILE_IMAGE: String = "User_Profile_Image"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE: String = "profileCompleted"

    /*
        fun showImageChooser(activity: Activity) {

    //    // An intent for launching the image selection of phone storage
    //        val galleryIntent = Intent()
    //        galleryIntent.setType("image/*");
    //        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
    //        // Launches the image selection of phone storage using the constant code
          // activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)

            val galleryIntent=Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            // Launches the image selection of phone storage using the constant code
            activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)


        }*/

     */
    fun getFileExtension(activity: Activity, uri: Uri?): String? {
        /*
        MimeTypeMap: two way that maps Mime-types to file extension and vice versa
        getSingleton():Get the singleton instance of MimeTypeMap
        getExtensionFromMimeType:Return the registered extension for the given Mime type
        contentResolver.getType:Return the Mime Type of the given content URL
*/

        return MimeTypeMap.getSingleton()
            .getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }


}
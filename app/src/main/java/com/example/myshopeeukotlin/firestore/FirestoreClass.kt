package com.example.myshopeeukotlin.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.SystemClock
import android.util.Log
import com.example.myshopeeukotlin.activities.LoginActivity
import com.example.myshopeeukotlin.activities.RegisterActivity
import com.example.myshopeeukotlin.activities.UserProfileActivity
import com.example.myshopeeukotlin.model.User
import com.example.myshopeeukotlin.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.System.currentTimeMillis


class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        // The 'users' is collection name. if the collection is already created then it will not create the same
        mFireStore.collection(Constants.USERS)
            // Document ID for users field. Here the document it is the Users ID.
            .document(userInfo.id)
            // Here the userInfo are Field and the setOption is set to merge. It is for if we want to merge later on instead of replacing the field
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                // Here call a function of base activity for transfering the result to it
                activity.userRegistrationSuccess()

            }.addOnFailureListener { exc ->
                activity.hideProgressDialog()
                Log.e(activity.javaClass.simpleName, "Error while registering the user .", exc)
            }

    }

    fun getCurrentUserID(): String {
        // An Instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

// A variable to assign the currentUserId if it is not null or else it will be balnk
        var currentUserID = ""
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }
        return currentUserID
    }

    fun getUserDetails(activity: Activity) {
        // Here we pass the collection name from which we want the data
        mFireStore.collection(Constants.USERS)
            // the document id to get the fields of user.
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())
                // Here we received the document snapshot which is converted in to the User Data model Object
                val user = document.toObject(User::class.java)!!

                val sharePreferences =
                    activity.getSharedPreferences(
                        Constants.MYSHOPPAL_PREFERENCES,
                        // data present inside the application
                        Context.MODE_PRIVATE
                    )
                val editor: SharedPreferences.Editor = sharePreferences.edit()
                editor.putString(
                    //Key : Constants.LOGGED_IN_USERNAME
                    // Value :  first and Last Name
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()


                //TODO step 6: Pass the result to login Activity.
                //Start
                when (activity) {
                    is LoginActivity -> {
                        // Call a function of base activity for transferring the result to it
                        activity.userLoggedInSuccess(user)

                    }
                }
                //End


            }

    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.userProfileUpdateSuccess()
                    }

                }
            }
            .addOnFailureListener { exc ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()

                    }
                }
                Log.e(activity.javaClass.simpleName, "Error while updating the user detail", exc)

            }

    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileUri: Uri?) {
        // Storage Reference
        val sRef: StorageReference = FirebaseStorage.getInstance().reference
            .child(
                Constants.USER_PROFILE_IMAGE
                        + System.currentTimeMillis() + "."
                        + Constants.getFileExtension(
                    activity, imageFileUri
                )
            )
        // Put file online
        sRef.putFile(imageFileUri!!)
            .addOnSuccessListener { taskSnapshot ->
                // The Image upload is Success

                Log.e(
                    "Firebase Image URL",
                    taskSnapshot.metadata!!.reference!!.downloadUrl!!.toString()
                )
                // Get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl!!.addOnSuccessListener {
                        uri ->

//                    Log.e("DownLoadable Image URL", uri.toString())
                    when(activity){
                        is UserProfileActivity->{
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }

                }
                    .addOnFailureListener{ exception->
                    when (activity) {
                        is UserProfileActivity -> {
                            activity.hideProgressDialog()
                        }
                    }
                    Log.e(
                        activity.javaClass.simpleName,
                        exception.message.toString(),
                        exception
                    )

                }

            }

    }


}
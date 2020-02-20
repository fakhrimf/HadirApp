package com.hadir.hadirapp.ui.register

import android.app.Application
import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.hadir.hadirapp.R
import com.hadir.hadirapp.model.TeacherModel
import com.hadir.hadirapp.ui.base.BaseAndroidViewModel
import com.hadir.hadirapp.utils.TEACHER_DATABASE_REF
import com.hadir.hadirapp.utils.TEACHER_STORAGE_REFERENCE

class RegisterViewModel (application: Application) : BaseAndroidViewModel(application) {
    private val database by lazy {
        FirebaseDatabase.getInstance().reference
    }

    private val storage by lazy {
        FirebaseStorage.getInstance().reference
    }

    fun addTeacher(teacherModel: TeacherModel, path: Uri): MutableLiveData<Boolean> {
        val id = "${database.push().key}"
        val added = MutableLiveData<Boolean>()
        val ref = storage.child(TEACHER_STORAGE_REFERENCE).child(id).child("${teacherModel.name}")
        makeText(context.getString(R.string.please_wait))
        ref.putFile(path).apply {
            addOnSuccessListener {
                ref.downloadUrl.apply {
                    addOnSuccessListener {
                        teacherModel.imageUri = it.toString()
                        database.child(TEACHER_DATABASE_REF).child(id).setValue(teacherModel)
                        makeText(context.getString(R.string.success_add))
                        added.value = true
                    }

                    addOnFailureListener {
                        added.value = false
                        Log.e("ERR", "addTeacher: Download Url Failure", it)
                    }
                }
            }

            addOnFailureListener {
                added.value = false
                Log.e("ERR", "addTeacher: Put File Failure", it)
            }
        }
        return added
    }

    fun getImageBitmap(contentResolver: ContentResolver, path: Uri) : Bitmap {
        @Suppress("DEPRECATION") return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, path))
        } else {
            MediaStore.Images.Media.getBitmap(contentResolver, path)
        }
    }
}
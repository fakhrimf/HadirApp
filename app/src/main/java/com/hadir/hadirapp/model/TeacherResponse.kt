package com.hadir.hadirapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherResponse(
    @SerializedName("results")
    val results: TeacherModel
) : Parcelable
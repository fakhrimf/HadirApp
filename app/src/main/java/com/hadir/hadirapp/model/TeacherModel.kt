package com.hadir.hadirapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherModel (
    var id: String? = "",
    var name: String? = "",
    var username: String? = "",
    var password: String? = "",
    var rfid_key: String? = ""
) : Parcelable
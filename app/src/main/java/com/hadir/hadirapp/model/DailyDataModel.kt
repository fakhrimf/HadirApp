package com.hadir.hadirapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DailyDataModel(
    var id: String = "",
    var teacherModel: TeacherModel? = null,
    var tanggal: String? = "",
    var waktu_masuk: String? = "",
    var waktu_keluar: String? = ""
) : Parcelable
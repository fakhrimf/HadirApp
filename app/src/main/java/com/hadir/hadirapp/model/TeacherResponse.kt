package com.hadir.hadirapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TeacherResponse(
    @SerializedName("id_guru")
    val id: Int,
    @SerializedName("grnrs")
    val generes: String,
    @SerializedName("nip")
    val nip: String?,
    @SerializedName("kategori_karyawan")
    val category: String?,
    @SerializedName("tahun_masuk")
    val tahun_masuk: String?,
    @SerializedName("aktif")
    val active: Int,
    @SerializedName("bio_lengkap")
    val teacherModel: TeacherModel
) : Parcelable
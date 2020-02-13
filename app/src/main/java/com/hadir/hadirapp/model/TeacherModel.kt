package com.hadir.hadirapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

// For Firebase
@Parcelize
data class TeacherModel(
    var generes: String?,
    var nip: String?,
    var active: Int,
    var category: String?,
    @SerializedName("id_bio")
    val id: Int,
    @SerializedName("nama_lengkap")
    val name: String?,
    @SerializedName("jenis_kelamin")
    val gender: String?,
    @SerializedName("tempat_lahir")
    val tempatLahir: String?,
    @SerializedName("tanggal_lahir")
    val tanggalLahir: String?,
    @SerializedName("agama")
    val agama: String?,
    var alamatFull: String?,
    @SerializedName("alamat_jalan")
    val alamatJalan: String?,
    @SerializedName("alamat_rt")
    val alamatRt: String?,
    @SerializedName("alamat_rw")
    val alamatRw: String?,
    @SerializedName("alamat_desa")
    val alamatDesa: String?,
    @SerializedName("alamat_kecamatan")
    val alamatKecamatan: String?,
    @SerializedName("alamat_kota")
    val alamatKota: String?,
    @SerializedName("alamat_pos")
    val alamatPos: String?,
    @SerializedName("telp_rumah")
    val telpRumah: String?,
    @SerializedName("telp_mobile")
    val telpMobile: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("email_smk")
    val emailSmk: String?
) : Parcelable
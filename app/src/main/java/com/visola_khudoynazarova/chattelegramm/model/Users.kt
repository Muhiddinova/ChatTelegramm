package com.visola_khudoynazarova.chattelegramm.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Users(
    var name: String = "",
    var status: String = "",
    var number: String = "",
    var uid: String = "",
    var haveAccount: Boolean = false,
    var image: String = ""

):Parcelable




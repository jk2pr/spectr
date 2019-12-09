package com.jk.spectr.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(

    val _id: String,
    val company: String,
    val website: String,
    val logo: String,
    val about: String,
    val members: ArrayList<Members>,
    var isSelected:Boolean,
    var isFav:Boolean,
    var isFollowing:Boolean

) : Parcelable {
    @Parcelize
    class Members(
        val _id: String,
        val age: String,
        val email: String,
        val phone: String,
        val name: Names


    ) : Parcelable {
        @Parcelize
        class Names(
            val first: String,
            val last: String

        ) : Parcelable {


            override fun toString(): String = "$first $last"
        }
    }


}

package com.dynamo.chiragtest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RocketData(
    @SerializedName("name") var name: String?,
    @SerializedName("company") var companyName: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("id") var RocketId: String?,
    @SerializedName("country") var location: String?,
    @SerializedName("first_flight") var launchDate: String?,
    @SerializedName("flickr_images") var rocketImageData: ArrayList<String>?
) : Parcelable



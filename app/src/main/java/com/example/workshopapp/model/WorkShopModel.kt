package com.example.workshopapp.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class WorkShopModel(
    @SerializedName("name") var name : String?,
    @SerializedName("image") var image : String?,
    @SerializedName("description") var description : String?,
    @SerializedName("text") var text : String?,
    @SerializedName("video") var video : String?
) : Comparable<WorkShopModel>, Parcelable{


    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun compareTo(other: WorkShopModel): Int {
        if (name == other.name &&
            image == other.image &&
            description == other.description &&
            text == other.text &&
            video == other.video)
            return 0
        return -1
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(text)
        parcel.writeString(video)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<WorkShopModel> {
        override fun createFromParcel(parcel: Parcel): WorkShopModel {
            return WorkShopModel(parcel)
        }

        override fun newArray(size: Int): Array<WorkShopModel?> {
            return arrayOfNulls(size)
        }
    }


}
package com.health.vistacan.encounter.model

import android.os.Parcel
import android.os.Parcelable

data class AdapterDataModel(val des:String, var selectedString:String=""): Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(des)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdapterDataModel> {
        override fun createFromParcel(parcel: Parcel): AdapterDataModel {
            return AdapterDataModel(parcel)
        }

        override fun newArray(size: Int): Array<AdapterDataModel?> {
            return arrayOfNulls(size)
        }
    }
}


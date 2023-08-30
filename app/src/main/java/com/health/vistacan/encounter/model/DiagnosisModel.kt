package com.health.vistacan.encounter.model

import android.os.Parcel
import android.os.Parcelable

data class DiagnosisModel(var description:String, var selectedString:String=""): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(description)
        parcel.writeString(selectedString)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DiagnosisModel> {
        override fun createFromParcel(parcel: Parcel): DiagnosisModel {
            return DiagnosisModel(parcel)
        }

        override fun newArray(size: Int): Array<DiagnosisModel?> {
            return arrayOfNulls(size)
        }
    }
}


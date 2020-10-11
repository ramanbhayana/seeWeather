package com.example.seeweather.dataclasses

import android.os.Parcelable
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonIgnoreProperties(ignoreUnknown = true)
data class Clouds(

    @field:JsonProperty("all")
    val all: Int? = null
) : Parcelable

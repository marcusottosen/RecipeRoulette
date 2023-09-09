package com.example.reciperoulette.data.model.dataClass

import android.os.Parcelable
import kotlinx.serialization.Serializable
import kotlinx.parcelize.Parcelize


@Serializable
data class Item(
    val id: Int? = 0,
    val title: String = "",
    val description: String = ""
)

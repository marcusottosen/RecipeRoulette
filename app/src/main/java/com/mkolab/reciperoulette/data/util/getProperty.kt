package com.mkolab.reciperoulette.data.util

import android.content.Context
import java.io.IOException
import java.util.Properties

@Throws(IOException::class)
fun getProperty(key: String?, context: Context): String? {
    val properties = Properties()
    val assetManager = context.assets
    val inputStream = assetManager.open("api.properties")
    properties.load(inputStream)
    return properties.getProperty(key)
}
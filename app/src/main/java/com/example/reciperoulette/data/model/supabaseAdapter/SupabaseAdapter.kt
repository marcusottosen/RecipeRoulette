package com.example.reciperoulette.data.model.supabaseAdapter

import android.util.Log
import com.example.reciperoulette.data.model.dataClass.Item
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.GoTrue
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.PropertyConversionMethod
import io.github.jan.supabase.postgrest.postgrest


class SupabaseAdapter {
    private val supabaseUrl = "https://waghuzctpmgexykusmhi.supabase.co"
    private val supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndhZ2h1emN0cG1nZXh5a3VzbWhpIiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODc5NDQ1NjQsImV4cCI6MjAwMzUyMDU2NH0.DdUoTFW6w-5Lb2yAP80_0ckq8K6sC_hgGVAur9_qdNs"

    private val client: SupabaseClient = createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Postgrest){
            defaultSchema = "schema" // default: "public"
            propertyConversionMethod = PropertyConversionMethod.CAMEL_CASE_TO_SNAKE_CASE
        }
        install(GoTrue) {
            alwaysAutoRefresh = false // default: true
            autoLoadFromStorage = true // default: true
        }
    }
    val gotrue = client.gotrue



    suspend fun getItems(): List<Item> {
        val supabaseResponse = client.postgrest["items"].select()
        val data = supabaseResponse.decodeList<Item>()
        Log.e("supabase getItem", data.toString())
        return data
    }

    suspend fun addItem(item: Item) {
        try {
            println(item)
            client.postgrest["items"].insert(item)
            Log.e("supabase", "Added new item: $item")
        }catch (e: Exception){
            Log.e("supabase", "Error addeding new item: ${e.message}")
            throw e // Rethrow the exception to let calling function know
        }
    }
}

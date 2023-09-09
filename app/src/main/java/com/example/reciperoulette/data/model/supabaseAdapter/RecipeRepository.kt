package com.example.reciperoulette.data.model.supabaseAdapter

import android.content.Context
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.RecipeList
import com.example.reciperoulette.data.util.readJsonFromAssets
import com.google.gson.Gson

class RecipeRepository {
    fun loadRecipeData(context: Context, fileName: String): List<Recipe> {
        try {
            val jsonString = readJsonFromAssets(context, fileName)
            val gson = Gson()
            val recipeList = gson.fromJson(jsonString, RecipeList::class.java)
            return recipeList.recipes
        } catch (e: Exception) {
            e.printStackTrace()
            return emptyList()
        }
    }
}
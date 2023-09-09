package com.example.reciperoulette.ui.viewmodel
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reciperoulette.data.model.dataClass.Recipe
import com.example.reciperoulette.data.model.dataClass.RecipeList
import com.example.reciperoulette.data.util.readJsonFromAssets
import com.google.gson.Gson



class RecipeDataViewModel : ViewModel() {
/*
    // LiveData to hold the recipe data
    private val _recipes = MutableLiveData<List<Recipe>>()
    val recipes: LiveData<List<Recipe>> = _recipes

    fun loadRecipeData(context: Context, fileName: String) {
        try {
            Log.d("json", "Loading data..")
            val jsonString = readJsonFromAssets(context, fileName)

            val gson = Gson()
            val recipeList = gson.fromJson(jsonString, RecipeList::class.java)
            _recipes.value = recipeList.recipes
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private var selectedRecipe: Recipe? = null

    fun selectRandomRecipe() {
        val recipesList = _recipes.value ?: emptyList()
        if (recipesList.isNotEmpty()) {
            selectedRecipe = recipesList.random()
        }
    }

    fun getRandomRecipe(): Recipe? {
        return selectedRecipe
    }*/

}

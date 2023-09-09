package com.example.reciperoulette.data.model.adapter

import android.util.Log
import com.example.reciperoulette.data.model.dataClass.RecipeA
import com.example.reciperoulette.data.model.dataClass.RecipeResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

suspend fun getAPIRecipe(tags: String): RecipeA? {
    return withContext(Dispatchers.IO) {
        //.url("https://api.spoonacular.com/recipes/recipes/random?tags=vegetarian%2Cdessert&number=1&limitLicense=true")

        val client = OkHttpClient()
        //Log.d("HTTP", "Requesting from API")
        val url = "https://api.spoonacular.com/recipes/random?number=1&apiKey=8849a719c9ce4f18992d8aa50c4fd637"
        val request = Request.Builder()
            .url(url)
            .addHeader("limitLicense","true")
            .addHeader("tags", tags)
            .get()
            .build()

        //Log.d("HTTP", "Request finished")

        val response = client.newCall(request).execute()
        val responseBody = response.body?.string()

        if (response.isSuccessful && responseBody != null) {
            try {

                //Log.d("HTTP", "Response successful")

                val moshi = Moshi.Builder()
                    .add(KotlinJsonAdapterFactory())
                    .build()

                val adapter = moshi.adapter(RecipeResponse::class.java)
                val recipeResponse = adapter.fromJson(responseBody)
                val recipe = recipeResponse?.recipes?.firstOrNull()

                return@withContext recipe

                if (recipe != null) {
                    Log.d("HTTP", "Returning recipe: ${recipe.title}")
                    Log.d("HTTP", "Title: ${recipe.title}")
                    Log.d("HTTP", "Ready in minutes: ${recipe.readyInMinutes}")
                    Log.d("HTTP", "Servings: ${recipe.servings}")
                    Log.d("HTTP", "Source URL: ${recipe.sourceUrl}")
                    Log.d("HTTP", "Image: ${recipe.image}")
                    Log.d("HTTP", "Summary: ${recipe.summary}")

                    recipe.extendedIngredients.forEach { ingredient ->
                        Log.d("HTTP", "Ingredient: ${ingredient.nameClean} - ${ingredient.amount} ${ingredient.unit}")
                    }

                    recipe.analyzedInstructions.forEach { instruction ->
                        instruction.steps.forEach { step ->
                            Log.d("HTTP", "Step ${step.number}: ${step.step}")
                        }
                    }
                } else {
                    Log.d("HTTP", "Failed to parse recipe from response.")
                }

            } catch (e: ClientRequestException) {
                Log.d("HTTP ERROR", e.toString())
                return@withContext null
            }
            Log.d("HTTP ERROR", "Returning null")
            return@withContext null
        } else {
            println("Failed to fetch recipe. Response code: ${response.code}")
            return@withContext null
        }
    }
}

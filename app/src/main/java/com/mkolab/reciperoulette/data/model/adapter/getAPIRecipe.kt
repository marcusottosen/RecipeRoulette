package com.mkolab.reciperoulette.data.model.adapter

import android.util.Log
import com.mkolab.reciperoulette.data.model.dataClass.Recipe
import com.mkolab.reciperoulette.data.model.dataClass.RecipeResponse
import com.mkolab.reciperoulette.data.model.dataClass.SearchCriteria
import com.mkolab.reciperoulette.data.util.responseInterpreter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.ktor.client.plugins.ClientRequestException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request


suspend fun getAPIRecipe(criteria: SearchCriteria, apiKey: String): Recipe? {
    return withContext(Dispatchers.IO) {
        //.url("https://api.spoonacular.com/recipes/recipes/random?tags=vegetarian%2Cdessert&number=1&limitLicense=true")





        Log.d("APIKEY", apiKey)

        val client = OkHttpClient()
        //Log.d("HTTP", "Requesting from API")
        /*val urlB = "https://api.spoonacular.com/recipes/random?number=1&apiKey=8849a719c9ce4f1
        val requestB = Request.Builder()
            .url(urlB)
            .addHeader("limitLicense","true")
            //.addHeader("tags", tags)
            .addHeader("cuisines", "Thai")
            .get()
            .build()
        */

        Log.d("HTTP Type", criteria.type.toString())
        val url = HttpUrl.Builder()
            .scheme("https")
            .host("api.spoonacular.com")
            .addPathSegment("recipes")
            .addPathSegment("complexSearch")
            .addQueryParameter("number", "1")
            .addQueryParameter("apiKey", apiKey)
            .addQueryParameter("sort", "random")
            //.addQueryParameter("cuisine", "italian")
            .addQueryParameter("type", criteria.type)

                //.addQueryParameter("diet", "vegetarian")
            .addQueryParameter("fillIngredients", "true")
            .addQueryParameter("addRecipeInformation", "true")
            .addQueryParameter("limitLicense","true")
            .addQueryParameter("instructionsRequired","true")
            .apply {
                criteria.toQueryMap().forEach{(key,value) ->
                    addQueryParameter(key, value)
                }
            }
            .build()

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        Log.d("HTTP request", request.toString())

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
                val recipe = recipeResponse?.results?.firstOrNull()
                if (recipe != null) {
                    Log.d("HTTP", "ID: ${recipe.id}")
                }
                //return@withContext recipe?.let<RecipeA, RecipeA> { responseInterpreter(recipe = it) }

                return@withContext recipe?.let<Recipe, Recipe> { responseInterpreter(recipe = it) }

                if (recipe != null) {  // debugging
                    Log.d("HTTP", "ID: ${recipe.id}")
                    Log.d("HTTP", "Returning recipe: ${recipe.title}")
                    Log.d("HTTP", "Title: ${recipe.title}")
                    Log.d("HTTP", "Ready in minutes: ${recipe.readyInMinutes}")
                    Log.d("HTTP", "Servings: ${recipe.servings}")
                    Log.d("HTTP", "Source URL: ${recipe.sourceUrl}")
                    Log.d("HTTP", "Image: ${recipe.image}")
                    Log.d("HTTP", "Summary: ${recipe.summary}")

                    recipe.extendedIngredients.forEach { ingredient ->
                        Log.d(
                            "HTTP",
                            "Ingredient: ${ingredient.nameClean} - ${ingredient.amount} ${ingredient.unit}"
                        )
                    }

                   recipe.cuisines.forEach { cuisine ->
                       Log.d("HTTP", "cuisine: $cuisine")
                   }

                    recipe.analyzedInstructions.forEach { instruction ->
                        instruction.steps.forEach { step ->
                            Log.d("HTTP", "Step ${step.number}: ${step.step}")
                        }
                    }
                } else {
                    Log.d("HTTP", "Failed to parse recipe from response.")
                }
                return@withContext recipe?.let<Recipe, Recipe> { responseInterpreter(recipe = it) }


            } catch (e: ClientRequestException) {
                Log.d("HTTP ERROR", e.toString())
                return@withContext null
            }
        } else {
            println("Failed to fetch recipe. Response code: ${response.code}")
            return@withContext null
        }
    }
}

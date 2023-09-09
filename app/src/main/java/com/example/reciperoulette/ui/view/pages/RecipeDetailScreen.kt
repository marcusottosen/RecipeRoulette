package com.example.reciperoulette.ui.view.pages

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel

@Composable
fun RecipeDetailsScreen(navController: NavController, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.recipe.observeAsState()

    LaunchedEffect(recipe) {
        // This block will execute whenever 'recipe' changes
    }

    if (recipe != null) {
        Column {
            Text(text = "Title: ${recipe!!.title}")
            Text(text = "Ready in minutes: ${recipe!!.readyInMinutes}")
            // ... Add other details
        }
    } else {
        Log.d("recipe", "fetching recipe...")
        Text("Fetching recipe...")
    }
}

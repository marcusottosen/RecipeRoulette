package com.mkolab.reciperoulette

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.mkolab.reciperoulette.ui.theme.RecipeRouletteTheme
import com.mkolab.reciperoulette.ui.viewmodel.RecipeViewModel
import com.mkolab.reciperoulette.data.util.Navigation
import com.mkolab.reciperoulette.ui.viewmodel.SharedViewModelFactory

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RecipeRouletteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val recipeViewModel =
                        ViewModelProvider(this, SharedViewModelFactory(applicationContext))[RecipeViewModel::class.java]

                    //val recipeViewModel: RecipeViewModel by viewModels()
                    val navController = rememberNavController()

                    Navigation(navController = navController, recipeViewModel)
                }
            }
        }
    }
}


package com.example.reciperoulette.ui.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciperoulette.R
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import com.example.reciperoulette.data.util.NavigationRoute


@Composable
fun Homepage(navController: NavController, recipeViewModel: RecipeViewModel) {

    val randomRecipeButtonClicked = remember { mutableStateOf(false) }

    // ###
    val navigateToDetails by recipeViewModel.navigateToDetails.observeAsState(initial = false)
    if (navigateToDetails) {
        navController.navigate(NavigationRoute.Loadingpage.route)
        recipeViewModel.onNavigationDone()
    }
    // ###


    // Define your logo image here (replace R.drawable.logo with your actual logo resource)
    //val logo = painterResource(id = R.drawable.logo_white_bg)
    val logo = painterResource(id = R.drawable.reciperoulettelogo3)
    val breakfastLogo = painterResource(id = R.drawable.breakfast)
    val dinnerLogo = painterResource(id = R.drawable.dinner)
    val dessertLogo = painterResource(id = R.drawable.dessert)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Logo
        Image(
            painter = logo,
            contentDescription = "Logo",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        )

        Text(
            text = "Recipe Roulette",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 50.dp)
        )

        // Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    recipeViewModel.fetchRecipe("breakfast")
                    /*
                    randomRecipeButtonClicked.value = true
                    // Pass the meal type (e.g., "breakfast") to the next screen
                    recipeViewModel.setSelectedMealType("breakfast")
                    recipeViewModel.selectRandomRecipe()*/
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(300.dp, 100.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(    verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = breakfastLogo,
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(80.dp)
                            .padding(end = 30.dp)
                    )
                    Text(
                        text = "Breakfast",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = {
                    recipeViewModel.fetchRecipe("main course")
                    /*
                    randomRecipeButtonClicked.value = true
                    // Pass the meal type (e.g., "breakfast") to the next screen
                    recipeViewModel.setSelectedMealType("dinner")
                    recipeViewModel.selectRandomRecipe()*/
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(300.dp, 100.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(    verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = dinnerLogo,
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(80.dp)
                            .padding(end = 50.dp)
                    )
                    Text(
                        text = "Dinner",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Button(
                onClick = {
                    recipeViewModel.fetchRecipe("dessert")

                    /*randomRecipeButtonClicked.value = true
                    // Pass the meal type (e.g., "breakfast") to the next screen
                    recipeViewModel.setSelectedMealType("dessert")
                    recipeViewModel.selectRandomRecipe()*/
                },
                modifier = Modifier
                    .padding(8.dp)
                    .size(300.dp, 100.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(    verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start) {
                    Image(
                        painter = dessertLogo,
                        contentDescription = "Logo",
                        modifier = Modifier
                            .height(75.dp)
                            .padding(end = 30.dp)
                    )
                    Text(
                        text = "Dessert",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(onClick = { navController.navigate(NavigationRoute.FilterPage.route) }) {
                Icon(imageVector = Icons.Default.Settings, contentDescription = "Close Icon")
            }

            Spacer(modifier = Modifier.height(100.dp))
            VegetarianSwitch(recipeViewModel)
            Spacer(modifier = Modifier.height(100.dp))

        }
    }

    /*if (randomRecipeButtonClicked.value) {
        val randomRecipe = recipeViewModel.getRecipe()
        if (randomRecipe != null) {
            // Navigate to the RecipePage and pass the selected meal type
            navController.navigate(NavigationRoute.Loadingpage.route) {
                launchSingleTop = true
            }
        }
    }*/
}

@Composable
fun VegetarianSwitch(recipeViewModel: RecipeViewModel) {

    Row() {
        Text(text = "Vegetarian", Modifier.padding(end = 10.dp))

        Switch(
            checked = recipeViewModel.vegetarianSwitch.value,
            colors = SwitchDefaults.colors(
                checkedTrackColor = MaterialTheme.colorScheme.primary
            ),
            onCheckedChange = {
                recipeViewModel.toggleVegetarianSwitch()
            },
            thumbContent = if (recipeViewModel.vegetarianSwitch.value) {
                {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            } else {
                null
            }
        )
    }
}



// Observe the LiveData
//val recipes by recipeViewModel.recipes.observeAsState(initial = emptyList())
// Display the recipe list using RecipeList Composable
//RecipeList(recipes = recipes)
/*@Composable
fun RecipeList(recipes: List<Recipe>) {
    Column {
        // Recipe List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(recipes) { recipe ->
                RecipeCard(recipe = recipe)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun RecipeCard(recipe: Recipe) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* Handle card click here */ }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Recipe Image
            /*Image(
                painter = painterResource(id = R.drawable.recipe_image), // Replace with actual image
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )*/

            Spacer(modifier = Modifier.height(16.dp))

            // Recipe Details
            Text(
                text = recipe.name,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )

            Text(
                text = recipe.cuisine,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Total Time: ${recipe.total_time}",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            )
        }
    }
}*/
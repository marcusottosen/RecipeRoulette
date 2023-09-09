package com.example.reciperoulette.ui.view.pages

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil.compose.rememberAsyncImagePainter


@Composable
fun RecipePage(navController: NavController, recipeViewModel: RecipeViewModel) {
    val recipe by recipeViewModel.recipe.observeAsState()

    /*LaunchedEffect(recipe) {
        // This block will execute whenever 'recipe' changes
    }*/


    val selectedRecipe by recipeViewModel.selectedRecipe.observeAsState()
    val sidePadding = Modifier.padding(horizontal = 10.dp)
    LazyColumn {
        recipe?.let { recipe ->
            item {
                // Display the recipe details
                Box() {
                    Image(
                        painter = rememberAsyncImagePainter(recipe.image), // Use Coil to load the image
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp) // Set the desired height for the image
                        ,
                        contentScale = ContentScale.Crop
                    )
                    BoxWithConstraints(
                        Modifier
                            .padding(0.dp)) {

                        Box(modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 192.dp)
                        ) {
                            Card(
                                modifier = Modifier
                                    .padding(50.dp, 0.dp)
                                    .fillMaxWidth()
                                    //.height(110.dp)
                                    ,//.clip(RoundedCornerShape(12.dp)),
                                elevation = CardDefaults.cardElevation(
                                    defaultElevation = 10.dp
                                )
                            ) {
                                Column(
                                    Modifier
                                        .height(100.dp)
                                        .background(Color(0xFFfff2e6))
                                ) {
                                    Text(//Card title
                                        text = recipe.title,
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.Black,
                                        modifier = Modifier.padding(10.dp, 15.dp)
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .background(Color.White)
                                            .height(80.dp)
                                    ) {
                                        Row(Modifier.padding(10.dp)) {
                                            Icon(
                                                imageVector = Icons.Default.AddCircle,
                                                contentDescription = null, // Provide a content description
                                                tint = Color.Black, // Customize the icon color
                                                modifier = Modifier.size(25.dp) // Adjust the size of the icon)
                                            )
                                            Text(text = recipe.readyInMinutes.toString(), Modifier.padding(start = 10.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(35.dp))
                
                // Ingredients
                Text(text = "Ingredients:", modifier = sidePadding, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                for (ingredient in recipe.extendedIngredients) {
                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        text = "${ingredient.nameClean}: ${ingredient.amount} ${ingredient.unit}",
                        modifier = sidePadding,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                Spacer(modifier = Modifier.height(35.dp))

                // Instructions (if available)
                val instructions = recipe.analyzedInstructions
                if (instructions.isNotEmpty()) {
                    Text(text = "Instructions:", modifier = sidePadding, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    instructions.forEach { instruction ->
                        instruction.steps.forEach { step ->
                            Text(
                                text = "${step.number}. ${step.step}",
                                modifier = sidePadding,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = {
                        navController.navigate(NavigationRoute.Homepage.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(text = "Back to Homepage")
                }
            }
        }

        if (recipe == null) {
            item {
                // Handle the case when no recipe is selected
                Text(text = "No recipe selected.", modifier = Modifier.padding(16.dp))
            }
        }
    }
}
/*Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = 192.dp)
        ) {
            Box(
                modifier = Modifier
                    .padding(50.dp, 0.dp)
                    .fillMaxWidth()
                    //.height(110.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)

            ) {
                Column(
                    Modifier
                        .height(100.dp)
                        .background(Color.Blue)
                ) {
                    Text(//Card title
                        text = recipe.name,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp, 15.dp)
                    )
                    /*Text(//Card title
                        text = recipe.cuisine,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp, 0.dp)

                    )*/
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Green)
                            .height(80.dp)
                    ) {
                        Row(Modifier.padding(10.dp)) {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = null, // Provide a content description
                                tint = Color.Black, // Customize the icon color
                                modifier = Modifier.size(25.dp) // Adjust the size of the icon)
                            )
                            Text(text = recipe.total_time, Modifier.padding(start = 10.dp))
                        }
                    }
                }
            }
        }*/

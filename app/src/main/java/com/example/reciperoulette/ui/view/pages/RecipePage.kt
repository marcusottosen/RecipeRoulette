package com.example.reciperoulette.ui.view.pages

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.reciperoulette.R
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.flow.forEach


@Composable
fun TextStat(num: String, type: String){
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = num,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = type,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun RecipePage(navController: NavController, recipeViewModel: RecipeViewModel) {//navController: NavController, recipeViewModel: RecipeViewModel
    val recipe by recipeViewModel.recipe.observeAsState()


    Log.d("RECIPE PAGE", "HERE")


    var isImageExpanded by remember { mutableStateOf(false) }

    val sidePadding = Modifier.padding(horizontal = 20.dp)

    LazyColumn(Modifier.fillMaxWidth()) {
        recipe?.let { recipe ->
            item { // Image and header
                //Box(
                //    modifier = Modifier
                //        .fillMaxWidth()
                //        .height(250.dp)
                //) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    //.background(Color(0xFFfff2e6)),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth(),
                        //.padding(20.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Box(modifier = Modifier
                            .padding(20.dp)
                            .clickable { navController.navigate(NavigationRoute.Homepage.route) }
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    }

                    Image(
                        painter = rememberAsyncImagePainter(
                            ImageRequest.Builder(LocalContext.current).data(data = recipe.image)
                                .apply(block = fun ImageRequest.Builder.() {
                                    crossfade(true)
                                    error(R.drawable.dinnerillu)
                                }).build()
                        ),
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 0.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )



                    /* Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(0.dp, 0.dp)
                            .offset(y = 60.dp)
                            .fillMaxWidth()
                            //.height(50.dp)
                            .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 1.dp)),
                        colors = CardDefaults.cardColors(Color.White)

                    ) {*/
                    Text(
                        text = recipe.title,
                        Modifier.padding(20.dp, 20.dp),
                        style = MaterialTheme.typography.titleLarge
                        //fontFamily = PoppinsFontFamily,
                        //fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = sidePadding
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                1.5.dp,
                                MaterialTheme.colorScheme.tertiary,
                                RoundedCornerShape(12.dp)
                            )
                    ) {
                        Row(
                            modifier = sidePadding
                                .fillMaxSize()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            TextStat(num = recipe.readyInMinutes.toString(), type = "minutes")
                            TextStat(num = recipe.servings.toString(), type = "servings")
                            TextStat(num = recipe.calories.toString(), type = "calories")
                            TextStat(num = recipe.protein.toString(), type = "protein")
                        }
                    }
                    Spacer(modifier = Modifier.height(70.dp))
                    //  Card(
                    //      modifier = Modifier
                    //          .offset(y = 25.dp)
                    //          .fillMaxWidth()
                    //          .height(50.dp)
                    //          .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 1.dp)),
                    //      colors = CardDefaults.cardColors(Color.Red)
//
                    //  ) {
                    //  }

                }

                //}

                /*Card(
                        modifier = Modifier
                            .align(Alignment.BottomCenter) // Aligns the center of the Card to the bottom of the Box
                            .padding(50.dp, 0.dp).offset(y=45.dp)
                            .fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
                    ) {
                        Column(
                            Modifier
                                //.height(100.dp)
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
                                    //.height(50.dp)
                            ) {
                                Row(Modifier.padding(10.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.schedule),
                                        contentDescription = "My Icon",
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Text(text = recipe.readyInMinutes.toString(), Modifier.padding(start = 10.dp, end = 30.dp))

                                    Icon(
                                        painter = painterResource(id = R.drawable.group_icon),
                                        contentDescription = "My Icon",
                                        modifier = Modifier.size(25.dp)
                                    )
                                    Text(text = recipe.servings.toString(), Modifier.padding(start = 10.dp))


                                }
                            }
                        }
                    }*/
                //}
            }


            // Occasions, dishtypes, cuisines
            // Summary

            item {// Ingredients
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(top = 20.dp)
                        //.background(Color(0xFFfff2e6)),
                    ) {


                        //Spacer(modifier = Modifier.height(20.dp))

                        Row(
                            modifier = sidePadding.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Ingredients",
                                modifier = Modifier.padding(bottom = 8.dp),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White
                                //fontSize = 16.sp,
                                //fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${recipe.extendedIngredients.size} items",
                                //modifier = sidePadding,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                                //fontSize = 16.sp,
                                //fontWeight = FontWeight.Bold
                            )
                        }
                        for (ingredient in recipe.extendedIngredients) {
                            Spacer(modifier = Modifier.height(5.dp))

                            Row(
                                modifier = sidePadding.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    Modifier.padding(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Canvas(
                                        modifier = Modifier
                                            .padding(end = 8.dp)
                                            .size(6.dp)
                                    ) {
                                        drawCircle(Color.Yellow)
                                    }
                                    Text(
                                        text = ingredient.nameClean,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color.White

                                        //modifier = Modifier.padding(start = 10.dp)
                                        //modifier = Modifier.weight(1f, fill = false)//.padding(start = 10.dp) // Push the next Text to the end
                                    )
                                }

                                Text(
                                    text = "${ingredient.amount} ${ingredient.unit}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = Color.White

                                    //fontSize = 14.sp,
                                    //fontWeight = FontWeight.Normal
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(40.dp))
                        Card(
                            modifier = Modifier
                                .offset(y = 10.dp)
                                .fillMaxWidth()
                                .height(50.dp)
                                .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp)),
                            colors = CardDefaults.cardColors(Color.White)

                        ) {}
                    }
                    // Card on top
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .offset(y = (-30).dp)
                            .height(50.dp)
                            .clip(RoundedCornerShape(40.dp, 40.dp, 0.dp, 0.dp)),
                        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary) //fff2e6
                    ) {
                        // Your card content here
                    }
                }
                /* FlowRow(
                     Modifier.fillMaxWidth(),
                     maxItemsInEachRow = 4,
                     verticalArrangement = Arrangement.spacedBy(10.dp),
                     horizontalArrangement = Arrangement.spacedBy(10.dp)
                 ) {
                     for (ingredient in recipe.extendedIngredients) {
                         //Spacer(modifier = Modifier.height(5.dp))
                         Box(
                             modifier = Modifier
                                 .size(130.dp)
                                 .padding(5.dp)
                                 .clip(RoundedCornerShape(12.dp)) // Corner radius
                                 .background(Color(0xFFfff2e6))

                         ) {
                             Text(
                                 text = "${ingredient.nameClean}: ${ingredient.amount} ${ingredient.unit}",
                                 modifier = sidePadding,
                                 fontSize = 14.sp,
                                 fontWeight = FontWeight.Normal
                             )
                         }
                     }
                 }*/
            }





            item { // Instructions
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(40.dp, 40.dp, 40.dp, 40.dp)),
                    colors = CardDefaults.cardColors(Color.White)

                ) {
                    Spacer(modifier = Modifier.height(1.dp))
                    val instructions = recipe.analyzedInstructions
                    /*
                if (instructions.isNotEmpty()) {
                    Text(
                        text = "Instructions:",
                        modifier = sidePadding,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                */

                    if (instructions.isNotEmpty()) {
                        Row(
                            modifier = sidePadding.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = "Instructions",
                                modifier = Modifier.padding(bottom = 8.dp),
                                style = MaterialTheme.typography.titleMedium
                                //fontSize = 16.sp,
                                //fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "${recipe.analyzedInstructions.sumOf { it.steps.size }} steps",
                                //modifier = sidePadding,
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.secondary
                                //fontSize = 16.sp,
                                //fontWeight = FontWeight.Bold
                            )
                        }

                        instructions.forEach { instruction ->
                            instruction.steps.forEach { step ->
                                Spacer(modifier = Modifier.height(10.dp))

                                Row(
                                    modifier = sidePadding//,verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = step.number.toString() + ".",
                                        modifier = Modifier.padding(end = 10.dp),
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text(
                                        text = " ${step.step}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {
                        navController.navigate(NavigationRoute.Homepage.route)
                    },
                    modifier = sidePadding
                        .fillMaxWidth()
                ) {
                    Text(text = "Back to Homepage")
                }
                Spacer(modifier = Modifier.height(15.dp))
                val context = LocalContext.current

                Text(
                    text = "Recipe Credit: " + recipe.creditsText,
                    modifier = sidePadding.clickable {
                        val openURL = Intent(Intent.ACTION_VIEW)
                        openURL.data = Uri.parse(recipe.sourceUrl)
                        context.startActivity(openURL)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        // if (recipe == null) {
        //     item {
        //         // Handle the case when no recipe is selected
        //         Text(text = "No recipe selected.", modifier = Modifier.padding(16.dp))
        //     }
        // }
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

package com.example.reciperoulette.ui.view.pages

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.reciperoulette.R
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.view.reuseables.MealPager
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel


data class MealData(
    val title: String,
    val imageResId: Int,
    val shadowColor: Color,
    val backgroundColor: Color
)

@Composable
fun Homepage(navController: NavController, recipeViewModel: RecipeViewModel) {
    Log.d("HOMEPAGE", "navigated to homepage")

    //val navigateToDetails by recipeViewModel.navigateToDetails.observeAsState(initial = false)
    //if (navigateToDetails) {
    //    recipeViewModel.onNavigationDone()
    //    navController.navigate(NavigationRoute.Loadingpage.route)
    //}

    val mealDataList = listOf(
        MealData("Drink", R.drawable.drink, Color(0xFF19830D), Color(0xFFA7DBA3)),
        MealData("Snack", R.drawable.snack, Color(0xFF6D0D83), Color(0xFFEBBCE7)),
        MealData("Breakfast", R.drawable.breakfastillu, Color(0xFFB1700C), Color(0xFFFEDAA3)),
        MealData("Main Course", R.drawable.dinnerillu, Color(0xFF0D1F83), Color(0xFFa3ade6)),
        MealData("Dessert", R.drawable.dessertillu, Color(0xFFC5190C), Color(0xFFE2A19C)),
        MealData("Soup", R.drawable.soup, Color(0xFF0D9186), Color(0xFF9CE2DA)),
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Recipe Roulette",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        Box (Modifier.height(600.dp).fillMaxWidth()) {
            MealPager(mealDataList, recipeViewModel, navController)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top=50.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
          //  IconButton(onClick = { navController.navigate(NavigationRoute.FilterPage.route) }) {
          //      Icon(imageVector = Icons.Default.Settings, contentDescription = "Filter Icon")
          //  }

            Button(
                onClick = { navController.navigate(NavigationRoute.FilterPage.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                //shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.filter),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(end = 16.dp)
                    )
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

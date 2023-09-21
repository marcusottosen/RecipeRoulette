package com.example.reciperoulette.data.util


import AnimatedView
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reciperoulette.ui.view.pages.FilterPage
import com.example.reciperoulette.ui.view.pages.Homepage
import com.example.reciperoulette.ui.view.pages.RecipePage
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel

@ExperimentalFoundationApi
@Composable
fun Navigation(navController: NavHostController, recipeViewModel: RecipeViewModel) {
    val startingDestination = NavigationRoute.Homepage.route


    NavHost(
        navController,
        startDestination = startingDestination
    ) {

        composable(NavigationRoute.Homepage.route) {
            Homepage(navController, recipeViewModel)
        }

        composable(NavigationRoute.Recipe.route) {
            RecipePage(navController, recipeViewModel)//navController = navController, recipeViewModel
        }

        composable(NavigationRoute.Loadingpage.route){
            AnimatedView(navController)
        }

        composable(NavigationRoute.FilterPage.route){
            FilterPage(navController, recipeViewModel)
        }


        /*
        composable(NavigationRoute.ItemDetails.route) {
            val jsonString = navController.previousBackStackEntry?.arguments?.getString("itemJson")
            Log.e("Navigation json", jsonString.toString())

            val itemModel = jsonString?.let { Json.decodeFromString(Item.serializer(), it) }
            Log.e("Navigation", itemModel.toString())

            if (itemModel != null) {
                ItemInfoPage(item = itemModel, navController = navController)
            } else
                AddItem(navController)
                Log.e("Nav to ItemDetails", "NULL ERROR")

        }*/

    }
}
/*itemModel?.let {
    Log.e("Navigation", "Should load ItemInfoPage 3")
    ItemInfoPage(item = it, navController = navController)
}*/

/*
inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelableExtra(key)
}

inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key)
}
*/
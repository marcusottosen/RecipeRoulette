package com.mkolab.reciperoulette.data.util

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mkolab.reciperoulette.ui.view.pages.FilterPage
import com.mkolab.reciperoulette.ui.view.pages.Homepage
import com.mkolab.reciperoulette.ui.view.pages.RecipePage
import com.mkolab.reciperoulette.ui.view.reuseables.AnimatedView
import com.mkolab.reciperoulette.ui.viewmodel.RecipeViewModel

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

        // When navigating from B to C
        composable(NavigationRoute.Recipe.route) {
            RecipePage(navController, recipeViewModel)

            // Handle the back button press in RecipePage
            BackHandler {
                navController.navigate(NavigationRoute.Homepage.route)
            }
        }


        composable(NavigationRoute.Loadingpage.route){
            AnimatedView(navController, recipeViewModel)
        }


        composable(
            route = NavigationRoute.FilterPage.route,
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Up,
                    animationSpec = tween(700)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(700)
                )
            }
        ) {
                FilterPage(navController, recipeViewModel)
            }



        // composable(NavigationRoute.Recipe.route) {
        //     RecipePage(navController, recipeViewModel)
        // }


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
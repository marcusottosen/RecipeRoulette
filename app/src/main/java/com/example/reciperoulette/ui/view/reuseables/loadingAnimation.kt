import android.media.MediaPlayer
import android.media.browse.MediaBrowser
import android.net.Uri
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.reciperoulette.R
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import kotlinx.coroutines.delay



@Composable
fun AnimatedView(navController: NavController, recipeViewModel: RecipeViewModel) {
    val navigateToDetails by recipeViewModel.navigateToDetails.observeAsState(initial = false)

    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_plate)
    )
    val speed = 1.5f

    // Text visibility states
    var isFindingRecipe by remember { mutableStateOf(true) }

    val updateString by remember {
        mutableStateOf("Finding the best random recipe for you")
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LottieAnimation(
                composition,
                modifier = Modifier.size(300.dp).padding(bottom = 20.dp),
                speed = speed,
                iterations = 3
            )
            if (isFindingRecipe) {
                Text(
                    text = "",
                    style = TextStyle(
                        color = Color.Black, // Customize the text color
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }

    if (navigateToDetails == 2) {
        //isFindingRecipe = false

        LaunchedEffect(key1 = composition) {
            delay(1000)
            recipeViewModel.onNavigationDone()
            navController.navigate(NavigationRoute.Recipe.route)
        }
    }

    if (navigateToDetails == 0) { // if error nav back to homepage
        //isFindingRecipe = false
        LaunchedEffect(key1 = composition) {
            delay(1000)
            navController.navigate(NavigationRoute.Homepage.route)
        }
    }
}

    // Use LaunchedEffect with delays to control the text animation
    //LaunchedEffect(key1 = composition) {
        //delay(1500) //500 for all
        //updateString = "Finding the best random recipe for you"

        //delay(300)
        //updateString = "Finding the best random recipe for you.."
//
        //delay(300)
        //updateString = "Finding the best random recipe for you..."
//
        //delay(300)
        //navController.navigate(NavigationRoute.Recipe.route)
       // {
       //     popUpTo(NavigationRoute.Homepage.route) {
       //         inclusive = false
       //     }
       // }

    //}
    //if (progress == 1.0f) {
    //    // Animation completes.
    //    navController.navigate(NavigationRoute.Recipe.route)
    //    //navController.navigate(NavigationRoute.Recipe.route)
    //}

/*    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.loading) // Replace with your JSON file
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center, // Center vertically
        horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
    ) {
        LottieAnimation(
            modifier = Modifier.size(200.dp),
            composition = composition
        )
    }*/


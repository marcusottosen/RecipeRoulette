import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.reciperoulette.R
import com.example.reciperoulette.data.util.NavigationRoute
import kotlinx.coroutines.delay

@Composable
fun AnimatedView(navController: NavController) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )
    val speed = 2.5f

    // Text visibility states
    var isFindingRecipe by remember { mutableStateOf(true) }

    var updateString by remember {
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
                iterations = 999
            )
            if (isFindingRecipe) {
                Text(
                    text = updateString,
                    style = TextStyle(
                        color = Color.Black, // Customize the text color
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                // Display dots with delays

            }
        }
    }

    // Use LaunchedEffect with delays to control the text animation
    LaunchedEffect(key1 = composition) {
        delay(500) //500 for all
        updateString = "Finding the best random recipe for you."

        delay(500)
        updateString = "Finding the best random recipe for you.."

        delay(500)
        updateString = "Finding the best random recipe for you..."

        delay(500)
        isFindingRecipe = false
        //navController.navigate(NavigationRoute.Recipe.route)
       // {
       //     popUpTo(NavigationRoute.Homepage.route) {
       //         inclusive = false
       //     }
       // }

    }
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
}

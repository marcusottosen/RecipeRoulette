package com.example.reciperoulette.ui.view.reuseables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults.elevatedCardColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.navigation.NavController
import com.example.reciperoulette.R
import com.example.reciperoulette.data.util.NavigationRoute
import com.example.reciperoulette.ui.view.pages.MealData
import com.example.reciperoulette.ui.viewmodel.RecipeViewModel
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealPager(mealDataList: List<MealData>, viewModel: RecipeViewModel, navController: NavController) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(Color(0xFFF1B3B3)),
    ) {
        val pagerState = rememberPagerState(pageCount = { mealDataList.size }, initialPage = 3)

        HorizontalPager(
            contentPadding = PaddingValues(horizontal = 40.dp),
            beyondBoundsPageCount = 2,
            state = pagerState,
            pageSize = PageSize.Fixed(330.dp),
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .wrapContentSize(Alignment.Center)
            ) {
                MealCard(
                    modifier = Modifier.fillMaxSize(),
                    pagerState = pagerState,
                    page = page,
                    mealData = mealDataList[page],
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
        Row(
            Modifier
                .height(50.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(mealDataList.size) { iteration ->
                val color = if (pagerState.currentPage == iteration) Color(0xFFa3ade6) else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)

                )
            }
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MealCard(
    pagerState: PagerState,
    page: Int,
    modifier: Modifier = Modifier,
    mealData: MealData,
    viewModel: RecipeViewModel,
    navController: NavController
) {
    val pageOffset = ((pagerState.currentPage - page) + pagerState
        .currentPageOffsetFraction).absoluteValue
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = {
                viewModel.fetchRecipe(mealType = mealData.title)
                navController.navigate(NavigationRoute.Loadingpage.route)
            })
            .wrapContentHeight()
            .shadow(16.dp, spotColor = mealData.shadowColor, shape = RoundedCornerShape(32.dp))
            ,
        shape = RoundedCornerShape(32.dp),
        colors = elevatedCardColors(containerColor = mealData.backgroundColor),

    ) {
        Column() {
            Image(
                modifier = Modifier
                    .padding(32.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .aspectRatio(1f)
                    .graphicsLayer {
                        val scale = lerp(1f, 0.75f, pageOffset)
                        scaleX *= scale
                        scaleY *= scale
                    },
                painter = painterResource(id = mealData.imageResId),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Box(
                modifier = Modifier
                    .height(150.dp * (1 - pageOffset))
                    .fillMaxWidth()
                    .graphicsLayer {
                        alpha = 1 - pageOffset
                    }
            ) {
                Column(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(mealData.title, style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.size(4.dp))
                    DragArea()
                }
            }
        }
    }
}


@Composable
private fun DragArea() {
    Box {
        Canvas(
            modifier = Modifier
                .padding(end = 4.dp)
                .fillMaxWidth()
                .height(60.dp)
                .clip(RoundedCornerShape(bottomEnd = 32.dp, bottomStart = 32.dp))
        ) {
            val dotSpacing = 16.dp.toPx()
            val dotsHorizontal = size.width / dotSpacing + 1
            val dotsVertical = size.height / dotSpacing + 1
            repeat(dotsHorizontal.roundToInt()) { horizontalDot ->
                repeat(dotsVertical.roundToInt()) { verticalDot ->
                    drawCircle(
                        Color.LightGray.copy(alpha = 0.5f),
                        radius = 2.dp.toPx(),
                        center = Offset(horizontalDot * dotSpacing + dotSpacing, verticalDot * dotSpacing + dotSpacing)
                    )
                }
            }
        }
    }
}

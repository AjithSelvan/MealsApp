package com.airbender.mealsapp.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.airbender.mealsapp.model.response.MealsResponse
import com.airbender.mealsapp.ui.theme.MealsAppTheme
import java.lang.Float.max
import kotlin.math.min

@Composable
fun SingleMealsDetailsScreen(meal: MealsResponse?, backPressedCallback: () -> Unit) {
//    val mealDetailsState = remember { mutableStateOf(MealAnimationDetails.Normal) }
//    val transition = updateTransition(targetState = mealDetailsState)
//    val changedDp = transition.animateDp(targetValueByState = { state -> state.value.dp })
//    val color = transition.animateColor(targetValueByState = { state -> state.value.color })
//    val borderWidth =  transition.animateDp(targetValueByState = { state -> state.value.borderWidth })
//    var isClicked by remember { mutableStateOf(false) }

    // scroller with column
//    val scrollState = rememberScrollState()
//    val offset = min(1f, 1 - (scrollState.value / 600f))
//    val imageSize =
//        animateDpAsState(targetValue = androidx.compose.ui.unit.max(100.dp, 250.dp * offset))
    val lazyScrollState = rememberLazyListState()
    var isBackPressed = remember { mutableStateOf(false) }
    val slideTransition =
        updateTransition(targetState = isBackPressed, label = "back press animation")
    val slideOffset by slideTransition.animateDp(
        label = "SlideOut",
        transitionSpec = { tween(durationMillis = 300, easing = FastOutSlowInEasing) }) { state ->
        if (state.value) 300.dp else 0.dp
    }
    val offset = min(
        1,
        1 - (remember { derivedStateOf { lazyScrollState.firstVisibleItemScrollOffset } }).value
                + remember { derivedStateOf { lazyScrollState.firstVisibleItemIndex } }.value
    )
    val imageSize = animateDpAsState(targetValue = max(100f, 250f * offset).dp)
    MealsAppTheme {
        Surface(
            color = MaterialTheme.colorScheme.surfaceBright,
            modifier = Modifier
                .fillMaxHeight()
                .statusBarsPadding()
                .navigationBarsPadding()
                .offset { IntOffset(slideOffset.value.toInt(), 0) }
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                Surface(
                    shadowElevation = 8.dp,
                    tonalElevation = 16.dp,
                    //color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current).data(meal?.imageUrl)
                            .transformations(CircleCropTransformation()).crossfade(true).build(),
                        contentDescription = "Meal Image",
                        modifier = Modifier
                            .size(imageSize.value)
                            .padding(16.dp)
                    )
                }
                Box(modifier = Modifier.padding(4.dp)) {
                    meal?.category?.let {
                        Text(
                            text = it,
                            fontStyle = FontStyle.Normal,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(4.dp),
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
//                Column(
//                    modifier = Modifier
//                        .align(Alignment.CenterHorizontally)
//                        .padding(10.dp)
//                        .fillMaxHeight()
//                        .shadow(1.dp, shape = RoundedCornerShape(1.dp))
//                        .verticalScroll(scrollState)
//                ) {
//                    meal?.description?.let {
//                        Text(
//                            text = it,
//                            textAlign = TextAlign.Start,
//                            fontSize = 30.sp,
//                            letterSpacing = 2.sp,
//                            lineHeight = 30.sp,
//                            modifier = Modifier.padding(8.dp)
//                        )
//                    }
//                }
                LazyColumn(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(10.dp)
                        .fillMaxHeight()
                        .shadow(1.dp, shape = RoundedCornerShape(1.dp)), state = lazyScrollState
                ) {
                    val mealLine: List<String> = meal?.description?.split('.') ?: emptyList()
                    items(mealLine) { item ->
                        Text(
                            text = item,
                            textAlign = TextAlign.Start,
                            fontSize = 30.sp,
                            letterSpacing = 2.sp,
                            lineHeight = 30.sp,
                            //modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
        BackHandler {
            isBackPressed = mutableStateOf(true)
            backPressedCallback()
        }
    }
}


//enum class MealAnimationDetails(val dp: Dp, val color: Color, val borderWidth: Dp) {
//    Normal(200.dp, Color.White, 4.dp),
//    Expanded(300.dp, Color.Gray, 8.dp)
//}

@Composable
@Preview
fun DetailsPreview() {
    SingleMealsDetailsScreen(MealsResponse("12", "asdhjgbsadi", "asdkansdbhjasd", "jhvsjhvad")) {}
}
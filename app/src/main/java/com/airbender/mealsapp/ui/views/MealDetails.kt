package com.airbender.mealsapp.ui.views

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.request.transformations
import coil3.transform.CircleCropTransformation
import com.airbender.mealsapp.model.response.MealsResponse

@Composable
fun MealsDetailsScreen(meal: MealsResponse?) {
    var mealDetailsState = remember { mutableStateOf(MealAnimationDetails.Normal) }
    val transition = updateTransition(targetState = mealDetailsState)
    val changedDp = transition.animateDp(targetValueByState = { state -> state.value.dp })
    val color = transition.animateColor(targetValueByState = { state -> state.value.color })
    val borderWidth =  transition.animateDp(targetValueByState = { state -> state.value.borderWidth })
    var isClicked by remember { mutableStateOf(false) }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Card(
            elevation = CardDefaults.outlinedCardElevation(10.dp),
            shape = CardDefaults.outlinedShape,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(meal?.imageUrl)
                    .transformations(CircleCropTransformation()).crossfade(true).build(),
                contentDescription = "Meal Image",
                modifier = Modifier
                    .size(changedDp.value)
                    .align(Alignment.CenterHorizontally)
                    .border(shape = CircleShape, color = color.value, width = borderWidth.value)
            )
        }
        Box(
            modifier = Modifier
                .padding(40.dp)
                .align(Alignment.CenterHorizontally)
        ) {

            meal?.category?.let {
                Text(
                    text = it,
                    fontStyle = FontStyle.Normal,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp)
                .fillMaxHeight(0.8f)
        ) {
            item {
                meal?.description?.let {
                    Text(
                        text = it,
                        textAlign = TextAlign.Justify,
                    )
                }
            }
        }
        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Button(onClick = {
                mealDetailsState.value = if (mealDetailsState.value == MealAnimationDetails.Normal)
                    MealAnimationDetails.Expanded
                else
                    MealAnimationDetails.Normal
            }) {
                Text(text = "Zoom!")
            }
        }
    }
}

enum class MealAnimationDetails(val dp: Dp, val color: Color, val borderWidth: Dp) {
    Normal(200.dp, Color.White, 4.dp),
    Expanded(300.dp, Color.Gray, 8.dp)
}
package com.example.buss

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.buss.ui.theme.BDarkGrey
import com.example.buss.ui.theme.BLightGrey


@Composable
fun Provinces(viewModel: MyViewModel, navController: NavHostController) {

    val response = MyViewModel().allCitiesLiveData.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = R.drawable.arow),
                contentDescription = "Back",
                modifier = Modifier
                    .clickable { navController.navigate("homePage") }
                    .padding(10.dp)
                    .size(30.dp),

                tint = BLightGrey,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Provinces", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 55.sp,
                    color = BDarkGrey,

                    ), modifier = Modifier.alpha(0.9f)
            )
            Spacer(modifier = Modifier.weight(3f))
        }
        Divider()
        if (response != null) {
            LazyColumn(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
                items(response.data!!.size) { index ->
                    if (response.data!![index].imageLink != null) {

                        CityCard(response.data!![index], onClick = {
                            viewModel.selectedCity = response.data!![index]
                            navController.navigate("provincePage")
                        })
                    }
                }
            }
        }
    }


}

@Composable
fun CityCard(dataCities: DataCities, onClick: () -> Unit = {}) {


    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(16f / 9f)
            .background(Color.White)
            .padding(8.dp)
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center

    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(dataCities.imageLink!!).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = dataCities.name!!,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 65.sp,
            ),
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 2.dp, y = 2.dp)
                .alpha(0.2f)
        )
        Text(
            text = dataCities.name!!, style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 65.sp,
            ), color = Color.White, modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.7f)
        )
    }

}
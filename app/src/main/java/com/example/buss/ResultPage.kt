package com.example.buss

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.decode.DataSource
import coil.request.ImageRequest
import com.example.buss.ui.theme.BDarkWhite
import com.example.buss.ui.theme.BLightGrey
import com.example.buss.ui.theme.FilterColor

@Composable
fun ResultPage(pageControllerNav: NavHostController, viewModel: MyViewModel) {
    Column {
        PageTop()
        Divider(color = BDarkWhite, thickness = 1.dp, modifier = Modifier.alpha(0.5f))
        Filters()
        Divider(
            color = BDarkWhite, thickness = 1.dp, modifier = Modifier
                .alpha(0.5f)
                .padding(top = 5.dp)
        )
        PageBottom(viewModel)
    }


}

@Composable
fun Filters() {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
    ) {
        FilterCard("Price")
        FilterCard("Duration")
        FilterCard("Departure Time")
        FilterCard("Arrival Time")
    }
}

@Composable
fun FilterCard(s: String) {

    Card(
        border = BorderStroke(1.dp, BDarkWhite),
        elevation = CardDefaults.cardElevation(3.dp),
        colors = CardDefaults.cardColors(
            containerColor = FilterColor,
        ),
        modifier = Modifier
            .padding(3.dp)
//            .clickable { Log.d("FilterCard", MyViewModel().findTripLiveData.toString()) },
    ) {
        Text(
            s,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(top = 12.dp, bottom = 12.dp, start = 22.dp, end = 22.dp)
        )
    }
}


@Composable
fun PageBottom(viewModel: MyViewModel) {

    val tripLiveData = viewModel.findTripLiveData.observeAsState().value


    if (tripLiveData != null) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(tripLiveData.data?.trips?.size!!) { index ->
                ResultCard(tripLiveData.data?.trips!![index])
                Divider(
                    color = BDarkWhite,
                    thickness = 1.dp,
                    modifier = Modifier
                        .alpha(0.5f)
                        .fillMaxWidth(0.9f)
                )
            }

        }
    }
}

@Composable
fun ResultCard(tripData: Trips) {
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(tripData.purchaseLink)) }




    Card(
        border = BorderStroke(1.dp, BDarkWhite),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .height(125.dp)
            .padding(top = 15.dp)
            .clickable {
                context.startActivity(intent)
            },
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.9f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp).fillMaxWidth(),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(tripData.busBrandLogoUrl)
                        .build(),
                    contentDescription = "BrandLogo",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.height(25.dp).weight(1f),
                )

                Text(
                    text = formatDepartureTime(tripData.departureTime!!),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = tripData.priceTotal.toString() + " TL",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(end = 10.dp).weight(1f),
                    textAlign = TextAlign.Right
                )
            }
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 10.dp),
                text = "${
                    strMaxLeng(
                        tripData.departureStationName!!,
                        15
                    )
                } -> ${strMaxLeng(tripData.arrivalStationName!!, 10)}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
            )
            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.clock),
                    contentDescription = "Clock",
                    modifier = Modifier.size(20.dp),
                    tint = BLightGrey
                )
                Text(
                    text = " ${tripData.hours}h ${tripData.minutes}m",
                    fontSize = 15.sp,
                    color = BLightGrey,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.seat),
                    contentDescription = "Seat",
                    modifier = Modifier.size(20.dp),
                    tint = BLightGrey
                )
                Text(
                    text = " 2+1",
                    fontSize = 15.sp,
                    color = BLightGrey,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.check),
                    contentDescription = "Check",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Green
                )
                Text(text = " Cancellation", fontSize = 15.sp, color = Color.Green)
            }

        }
    }

}

fun strMaxLeng(input: String, maxLength: Int): String {
    return if (input.length > maxLength) {
        input.substring(0, maxLength)
    } else {
        input
    }
}


@Composable
fun PageTop() {
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Icon(
            painter = painterResource(id = R.drawable.arow),
            contentDescription = "Back",
            modifier = Modifier
                .clickable { pageControllerNav.value?.navigate("homePage") }
                .padding(10.dp)
                .size(30.dp),
            tint = BLightGrey,
        )
        Spacer(modifier = Modifier.width(5.dp))
        Column {
            Text(
                "${departureCity.value} -> ${destinationCity.value}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "${_selectedDate.dayOfMonth} ${
                    _selectedDate.month.toString().substring(0, 3)
                } ${_selectedDate.dayOfWeek.toString().substring(0, 3)}",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = BLightGrey
            )
        }
    }
}


fun formatDepartureTime(departureTime: String): String {
    departureTime.find { it == 'T' }?.let {
        return departureTime.substring(11, 16)
    }
    return ""
}
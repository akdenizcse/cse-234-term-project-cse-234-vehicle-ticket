package com.example.buss

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.buss.ui.theme.BDarkGrey
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.painterResource
import com.example.buss.ui.theme.BBlue
import com.example.buss.ui.theme.BDarkWhite
import com.example.buss.ui.theme.BLightBlue
import com.example.buss.ui.theme.BLightGrey
import com.example.buss.ui.theme.BNeonBlue


@Composable
fun BusPage(myViewModel: MyViewModel) {
    val selectedBus = myViewModel.selectedBusBrand
    val response = myViewModel._busBrandComments.collectAsState()


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

        ) {
        Spacer(modifier = Modifier.height(5.dp))
        BusCard(selectedBus = selectedBus!!, modifier = Modifier.fillMaxWidth())
        Text(
            text = selectedBus.name!!,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp,
            ),
            color = BDarkGrey,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp)
        )
        Text(
            text = selectedBus.description!!,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
            ),
            color = BDarkGrey,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
        Divider(modifier = Modifier.height(1.dp))
        Spacer(modifier = Modifier.height(10.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "User Reviews",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                ),
                color = BDarkGrey,
                modifier = Modifier
                    .padding(horizontal = 20.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(containerColor = BBlue),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .height(50.dp)
                    .width(150.dp)
                    .padding(horizontal = 10.dp)
            ) {
                Text(
                    text = "Add Review",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                    ),
                    color = Color.White,
                )
            }
        }
        when(response ) {
            null -> {
                Log.d("BusPage", "response is null")
            }
            else -> {
                for (i in 0 until response.value.data.size) {
                    Log.d("BusPage", RetrofitClient.BEARER_TOKEN)
                    Spacer(modifier = Modifier.height(10.dp))
                    ReviewCard(
                        userName = response.value.data[i].userName!!,
                        review = response.value.data[i].commentText!!,
                        starCount = response.value.data[i].rate!!
                    )
                }
            }
        }
    }
}

@Composable
fun BusCard(selectedBus: DataBus, modifier: Modifier = Modifier) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, BLightBlue), contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(selectedBus.imageLink!!)
                .build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }


}

@Composable
fun ReviewCard(userName: String, review: String, starCount: Int) {


    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(150.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(BLightBlue)
            .border(1.dp, BLightGrey),
        contentAlignment = Alignment.Center

    ) {

        Column(
            modifier = Modifier.fillMaxWidth(0.9f),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = userName,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                ),
                color = BDarkGrey,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.yellowstar),
                    contentDescription = "Arrow",
                    modifier = Modifier.height(20.dp),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = if (starCount > 1) R.drawable.yellowstar else R.drawable.star),
                    contentDescription = "Arrow",
                    modifier = Modifier.height(20.dp),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = if (starCount > 2) R.drawable.yellowstar else R.drawable.star),
                    contentDescription = "Arrow",
                    modifier = Modifier.height(20.dp),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = if (starCount > 3) R.drawable.yellowstar else R.drawable.star),
                    contentDescription = "Arrow",
                    modifier = Modifier.height(20.dp),
                    tint = Color.Unspecified
                )
                Icon(
                    painter = painterResource(id = if (starCount > 4) R.drawable.yellowstar else R.drawable.star),
                    contentDescription = "Arrow",
                    modifier = Modifier.height(20.dp),
                    tint = Color.Unspecified
                )
            }
            Text(
                text = review, style = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontSize = 15.sp,
                ),
                color = BDarkGrey,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(vertical = 10.dp)
            )
        }

    }

}


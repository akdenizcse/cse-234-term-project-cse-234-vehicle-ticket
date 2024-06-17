package com.example.buss

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch


@Composable
fun Bus(viewModel: MyViewModel, navController: NavHostController) {
    val coroutineScope = rememberCoroutineScope()
    val response = MyViewModel().busBrandsLiveData.observeAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(70.dp)  ) {
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
                text = "Bus Companies", style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 40.sp,
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

                        BusCard(response.data!![index], onClick = {
                            viewModel.selectedBusBrand = response.data!![index]
                            coroutineScope.launch {
                                viewModel.getBusBrandComments()
                            }
                            navController.navigate("busPage")
                        })
                    }
                }
            }
        }
    }


}

@Composable
fun BusCard(dataBus: DataBus, onClick: () -> Unit = {}) {


    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(150.dp)
            .background(Color.White)
            .padding(8.dp)
            .clickable { onClick() }
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, BLightGrey),
        contentAlignment = Alignment.Center

    ) {
        Row ( verticalAlignment = Alignment.CenterVertically){
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current).data(dataBus.imageLink!!)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = dataBus.name!!,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    ),
                    color = BDarkGrey,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row (verticalAlignment = Alignment.CenterVertically){
                    Icon(
                        painter = painterResource(id = R.drawable.yellowstar),
                        contentDescription = "Arrow",
                        modifier = Modifier.height(20.dp),
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.yellowstar),
                        contentDescription = "Arrow",
                        modifier = Modifier.height(20.dp),
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.yellowstar),
                        contentDescription = "Arrow",
                        modifier = Modifier.height(20.dp),
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.yellowstar),
                        contentDescription = "Arrow",
                        modifier = Modifier.height(20.dp),
                        tint = Color.Unspecified
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.star),
                        contentDescription = "Arrow",
                        modifier = Modifier.height(20.dp),
                        tint = Color.Unspecified
                    )
                }
            }

        }

    }

}
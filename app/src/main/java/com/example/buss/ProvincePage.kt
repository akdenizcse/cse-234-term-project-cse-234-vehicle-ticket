package com.example.buss

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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


@Composable
fun ProvincePage(myViewModel: MyViewModel) {
    val dataCities = myViewModel.selectedCity

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),

    ) {
        Spacer(modifier = Modifier.height(5.dp))
        ProvinceCard(dataCities = dataCities!!)
        Text(
            text = dataCities.name!!,
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
            text = dataCities.description!!,
            style = TextStyle(
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
            ),
            color = BDarkGrey,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(10.dp)
        )

    }
}

@Composable
fun ProvinceCard(dataCities: DataCities, modifier: Modifier = Modifier) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f)
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp)), contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(dataCities.imageLink!!).build(),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )
    }


}
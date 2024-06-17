package com.example.buss

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.buss.ui.theme.BDarkGrey
import com.example.buss.ui.theme.BLightBlue
import com.example.buss.ui.theme.BLightGrey
import com.example.example.AuthResponse
import com.example.example.RegisterResponse
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@Composable
fun Profile(viewModel: MyViewModel, pageControllerNav: NavHostController) {


    val response = viewModel.authResponse.observeAsState()

    LaunchedEffect(key1 = response) {
        viewModel.getFollowedTrips()
    }
    //followed buses
    //logout
    Column(
        modifier = Modifier
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Text(
            text = "Followed Buses",
            style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold, color = BDarkGrey),
        )
        Column {
            if (viewModel.followedTrips.value == null) {
                Text(text = "No followed buses", modifier = Modifier.padding(top = 20.dp))
            } else {

                viewModel.followedTrips.value?.data?.forEach {
                    FollowCard(dataFollowTrip = it)
                }
            }
//            LazyColumn {
//                items(viewModel.followedTrips.value!!.data.size) { trip ->
//                    FollowCard(dataFollowTrip = viewModel.followedTrips.value!!.data[trip])
//                }
//            }
        }

        OutlinedButton(
            onClick = { pageControllerNav.navigate("loginPage") },
            border = BorderStroke(2.dp, Color.Red),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.power),
                    contentDescription = "Logout"
                )
                Text(
                    text = " Logout",
                    style = TextStyle(
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Red
                    ),
                )
            }
        }
    }
}

@Composable
fun FollowCard(dataFollowTrip: DataFollowTrip) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .height(100.dp)
            .padding(8.dp)
            .clickable { /*TODO*/ }
            .clip(RoundedCornerShape(10.dp))
            .background(BLightBlue)
            .border(1.dp, BLightGrey),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = dataFollowTrip.city1Name.toString() + " ---> ",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = dataFollowTrip.city2Name.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text(
                text = dataFollowTrip.date.toString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

        }

    }


}
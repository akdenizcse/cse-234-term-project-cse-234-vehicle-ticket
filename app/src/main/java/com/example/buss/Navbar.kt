package com.example.buss

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.delay


@Composable
fun BottomNavigationBar(
    navController: NavController,
    modifier: Modifier,
    viewModel: MyViewModel
) {
    val state = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(key1 =  viewModel.followedTrips) {
        delay(1000)
        if(RetrofitClient.BEARER_TOKEN.isNotEmpty())
        {
            state.value = true
        }
    }
    Box(
        modifier = modifier
    ) {
        Row(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.home),
                    contentDescription = "Home",
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            navController.navigate("homePage")
                        })
                Text(text = "Home")
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bus),
                    contentDescription = "bus",
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            navController.navigate("bus")
                        })
                Text(text = "Bus")
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.province),
                    contentDescription = "Province",
                    modifier = Modifier
                        .weight(2f)
                        .clickable {
                            navController.navigate("provinces")
                        })
                Text(text = "Province")
            }

            AnimatedVisibility(visible = state.value , modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .weight(1f),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.bell),
                        contentDescription = "profile",
                        modifier = Modifier
                            .weight(2f)
                            .clickable {
                                navController.navigate("profile")
                            })
                    Text(text = "Profile")
                }
            }

        }
    }

}

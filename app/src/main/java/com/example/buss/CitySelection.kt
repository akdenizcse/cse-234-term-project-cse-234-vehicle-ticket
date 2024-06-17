package com.example.buss

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buss.ui.theme.BBlackLight
import com.example.buss.ui.theme.BBlue
import com.example.buss.ui.theme.BDarkWhite
import com.example.buss.ui.theme.BNeonBlue
import com.example.buss.ui.theme.BNeonDarkBlue

var textFieldValue = mutableStateOf("")

@Composable
fun CitySelection() {
    Column (horizontalAlignment = Alignment.CenterHorizontally){

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(value = textFieldValue.value, onValueChange = { textFieldValue.value = it },
            label = {
                Text(
                    "Search City",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.background(Color.Transparent)
                )
            },

            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(8.dp),
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = BNeonDarkBlue,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),

        )

        Divider(color = BDarkWhite, thickness = 1.dp, modifier = Modifier.alpha(0.5f))

        CitySelectionLazyColumn()
    }
}

@Composable
fun CitySelectionLazyColumn(
) {
    val cities = MyViewModel().cityListLiveData.observeAsState().value

    if (cities != null) {
        LazyColumn {
            items(cities.size) { index ->
                if (cities[index].contains(textFieldValue.value, ignoreCase = true)) {
                    CityButton(city = cities[index])
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

}

@Composable
fun CityButton(city: String) {
    Box(modifier = Modifier
        .padding(8.dp)
        .height(50.dp)
        .fillMaxWidth(0.9f)
        .clickable {
            pageControllerNav.value?.navigate("homePage")
            CitySetter(city)
        }) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.location),
                contentDescription = "City Icon",
                tint = BBlue,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                modifier = Modifier.padding(8.dp),
                text = city,
                color = Color.Black,
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}
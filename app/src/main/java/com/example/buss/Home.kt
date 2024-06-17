package com.example.buss

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.buss.ui.theme.BBlue
import com.example.buss.ui.theme.BDarkWhite
import com.example.buss.ui.theme.BLightGrey
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.time.LocalDate


var departureCity = mutableStateOf("Antalya")
val destinationCity = mutableStateOf("Istanbul")
val isdeparture = mutableStateOf(false)

fun CitySetter(text: String = "Istanbul") {
    if (isdeparture.value) {
        departureCity.value = text
    } else {
        destinationCity.value = text
    }
}

var _selectedDate by mutableStateOf(LocalDate.now())

val pageControllerNav = mutableStateOf<NavHostController?>(null)

@Composable
fun Home(navController: NavHostController, viewModel: MyViewModel) {
    pageControllerNav.value = navController
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BBlue)
    )
    {
        TopBox()
        SearchBox(viewModel, navController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox(viewModel: MyViewModel, navController: NavHostController) {
    //context
    val context = LocalContext.current
    var selectedDate by remember { mutableStateOf(_selectedDate) }

    val todayColor = if (selectedDate == LocalDate.now()) {
        BBlue
    } else {
        BLightGrey
    }

    val tomorrowColor = if (selectedDate == LocalDate.now().plusDays(1)) {
        BBlue
    } else {
        BLightGrey
    }
    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp))
            .background(Color.White)
            .height(50.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(top = 35.dp)
        ) {

            SearchCard(
                departureCity.value,
                "Origin",
                Modifier.weight(1f),
                click = {
                    isdeparture.value = true
                    pageControllerNav.value?.navigate("citySelection")
                })
            Image(
                painter = painterResource(id = R.drawable.right),
                contentDescription = "right",
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp),
                alignment = Alignment.Center, alpha = 0.4f
            )
            SearchCard(
                destinationCity.value,
                "Destination",
                Modifier.weight(1f),
                click = {
                    isdeparture.value = false
                    pageControllerNav.value?.navigate("citySelection")
                })
        }
        Card(
            onClick = {
                dateDialogState.show()
            },
            border = BorderStroke(1.dp, BDarkWhite),
            elevation = CardDefaults.cardElevation(10.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
            ),
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(125.dp)
                .padding(top = 15.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(

                    modifier = Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.End
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.calendar),
                            contentDescription = "search",
                            modifier = Modifier
                                .size(20.dp)
                                .padding(end = 5.dp),
                            tint = Color.Black
                        )
                        Text(
                            text = "Departure",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Medium,
                            color = BLightGrey
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            text = selectedDate.dayOfMonth.toString(),
                            fontSize = 50.sp,
                            fontWeight = FontWeight.ExtraBold,
                            modifier = Modifier.padding(start = 6.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = capitalizeFirstLetter(
                                    selectedDate.month.toString().substring(0, 3)
                                ),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BLightGrey
                            )
                            Text(
                                text = capitalizeFirstLetter(
                                    selectedDate.dayOfWeek.toString().substring(0, 3)
                                ),
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold, color = BLightGrey
                            )
                        }
                    }
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    DayCardButton(
                        text = "Today",
                        weight = Modifier
                            .weight(1f)
                            .padding(top = 10.dp, bottom = 5.dp),
                        color = todayColor,
                        click = {
                            _selectedDate = LocalDate.now()
                            selectedDate = LocalDate.now()
                        }
                    )
                    DayCardButton(
                        text = "Tomorrow",
                        weight = Modifier
                            .weight(1f)
                            .padding(top = 5.dp, bottom = 10.dp),
                        color = tomorrowColor,
                        click = {
                            _selectedDate = LocalDate.now().plusDays(1)
                            selectedDate = LocalDate.now().plusDays(1)
                        }
                    )

                }

            }
        }
        SearchButton(viewModel)
        if (dateDialogState.showing) {
            val datePickerState = rememberDatePickerState()
            val state = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)
            DatePickerDialog(
                colors = DatePickerDefaults.colors(
                    containerColor = Color.White,
                ),
                onDismissRequest = {
                    dateDialogState.hide()
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            dateDialogState.hide()
                            var date = "no selection"
                            if (datePickerState.selectedDateMillis != null) {
                                //if selected date is lower than today
                                if (LocalDate.ofEpochDay(datePickerState.selectedDateMillis!! / 86400000) > LocalDate.now()) {
                                    _selectedDate = LocalDate.ofEpochDay(
                                        datePickerState.selectedDateMillis!! / 86400000
                                    )
                                    selectedDate = LocalDate.ofEpochDay(
                                        datePickerState.selectedDateMillis!! / 86400000
                                    )
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Please select a date after today",
                                        Toast.LENGTH_SHORT,

                                        ).show()
                                }
                            }

                        },
                    ) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            dateDialogState.hide()
                        }
                    ) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(
                    state = datePickerState,
                    colors = DatePickerDefaults.colors(
                        selectedDayContentColor = Color.White,
                        selectedDayContainerColor = BLightGrey,
                    )
                )
            }

        }



        RecommendationCards(viewModel, navController)


    }
}

@Composable
fun RecommendationCards(viewModel: MyViewModel, navController: NavHostController) {

    Spacer(modifier = Modifier.height(15.dp))

    Text(
        text = "Recommendations",
        color = BLightGrey,
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold
    )
    val response = MyViewModel().allCitiesLiveData.observeAsState().value

    if (response != null) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {

            //increase index +5
            for (index in 15 until 45 step 7) {
                CityCard(response.data!![index], onClick = {
                    viewModel.selectedCity = response.data!![index]
                    navController.navigate("provincePage")
                })

            }


        }
    }
}


//    Spacer(modifier = Modifier.height(10.dp))
//
//    Spacer(modifier = Modifier.height(10.dp))
//    ImageCard(cityName = "Antalya", imageResId = R.drawable.antalya, onclick = {
//        viewModel.selectedCity = response?.data!!.find { it.name == "antalya" }!!
//        navController.navigate("provincePage")
//    })
//    Spacer(modifier = Modifier.height(10.dp))
//    ImageCard(cityName = "Ankara", imageResId = R.drawable.ankara, onclick = {
//        viewModel.selectedCity = response?.data!!.find { it.name == "ankara" }!!
//        navController.navigate("provincePage")
//    })
//    Spacer(modifier = Modifier.height(10.dp))
//    ImageCard(cityName = "Izmir", imageResId = R.drawable.izmir, onclick = {
//        viewModel.selectedCity = response?.data!!.find { it.name == "izmir" }!!
//        navController.navigate("provincePage")
//    })
//    Spacer(modifier = Modifier.height(10.dp))
//    ImageCard(cityName = "Bursa", imageResId = R.drawable.bursa, onclick = {
//        viewModel.selectedCity = response?.data!!.find { it.name == "bursa" }!!
//        navController.navigate("provincePage")
//    })




@Composable
fun ImageCard(cityName: String, imageResId: Int, onclick: () -> Unit = {}) {

    Box(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .aspectRatio(16f / 9f)
            .background(Color.White)
            .padding(8.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable { onclick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            text = cityName,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 70.sp,
            ),
            color = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.Center)
                .offset(x = 2.dp, y = 2.dp)
                .alpha(0.2f)
        )
        Text(
            text = cityName,
            style = TextStyle(
                fontWeight = FontWeight.ExtraBold,
                fontSize = 70.sp,
            ),
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .alpha(0.7f)
        )
    }

}


@Composable
fun SearchButton(viewModel: MyViewModel) {
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            Log.d(
                "${_selectedDate.dayOfMonth}.${_selectedDate.monthValue}.${_selectedDate.year}",
                "Search Button Clicked"
            )
            coroutineScope.launch {
                viewModel.findTrip(
                    departureCity.value,
                    destinationCity.value,
                    "${_selectedDate.dayOfMonth}.0${_selectedDate.monthValue}.${_selectedDate.year}",
                    1
                )
                pageControllerNav.value?.navigate("resultPage")
            }


        },
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth(0.9f)
            .padding(top = 15.dp, bottom = 10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = BBlue
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(
            text = "Find Ticket",
            color = Color.White,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun DayCardButton(
    text: String,
    weight: Modifier,
    color: Color = BLightGrey,
    click: () -> Unit = {}
) {
    OutlinedButton(
        shape = RoundedCornerShape(10.dp),
        onClick = click,
        modifier = weight
            .height(50.dp)
            .fillMaxWidth(0.7f),
        border = BorderStroke(1.dp, color)

    ) {
        Text(
            text = text,
            color = color,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SearchCard(city: String, direction: String, weight: Modifier, click: () -> Unit = {}) {
    Card(
        border = BorderStroke(1.dp, BDarkWhite),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = weight
            .height(100.dp)
            .clickable { click() },
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = direction,
                modifier = Modifier.padding(top = 10.dp),
                color = BLightGrey,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = city,
                modifier = Modifier.padding(top = 10.dp, bottom = 15.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun TopBox() {
    Box(
        modifier = Modifier
            .background(BBlue)
            .height(150.dp)
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BBlue),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                "BuScanner",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Normal,
                    fontSize = 60.sp,
                    color = Color.White
                ),
                modifier = Modifier

                    .padding(
                        bottom = 10.dp
                    )
            )
        }


    }
}

val turkishCitiesList = listOf(
    "Adana", "Adıyaman", "Afyonkarahisar", "Ağrı", "Aksaray", "Amasya", "Ankara",
    "Antalya", "Ardahan", "Artvin", "Aydın", "Balıkesir", "Bartın", "Batman",
    "Bayburt", "Bilecik", "Bingöl", "Bitlis", "Bolu", "Burdur", "Bursa",
    "Çanakkale", "Çankırı", "Çorum", "Denizli", "Diyarbakır", "Düzce", "Edirne",
    "Elazığ", "Erzincan", "Erzurum", "Eskişehir", "Gaziantep", "Giresun", "Gümüşhane",
    "Hakkâri", "Hatay", "Iğdır", "Isparta", "İstanbul", "İzmir", "Kahramanmaraş",
    "Karabük", "Karaman", "Kars", "Kastamonu", "Kayseri", "Kilis", "Kırıkkale",
    "Kırklareli", "Kırşehir", "Kocaeli", "Konya", "Kütahya", "Malatya", "Manisa",
    "Mardin", "Mersin", "Muğla", "Muş", "Nevşehir", "Niğde", "Ordu", "Osmaniye",
    "Rize", "Sakarya", "Samsun", "Şanlıurfa", "Siirt", "Sinop", "Sivas", "Şırnak",
    "Tekirdağ", "Tokat", "Trabzon", "Tunceli", "Uşak", "Van", "Yalova", "Yozgat",
    "Zonguldak"
)

fun capitalizeFirstLetter(input: String): String {
    if (input.isEmpty()) {
        return input
    }
    return input.substring(0, 1).uppercase() + input.substring(1).lowercase()
}
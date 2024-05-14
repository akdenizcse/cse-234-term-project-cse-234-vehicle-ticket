package com.example.buss

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.derivedStateOf
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate


val globalCounter = mutableStateOf(0)

@Composable
fun Home() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BBlue)
    )
    {
        TopBox()
        SearchBox()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBox() {
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val todayColor = if (selectedDate == LocalDate.now()) {
        BBlue
    } else {
        BLightGrey
    }

    val tomorrowColor = if (selectedDate == LocalDate.now().plusDays(1)) {
        Color.Blue
    } else {
        Color.Gray
    }
    val dateDialogState = rememberMaterialDialogState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp))
            .background(Color.White)
            .height(50.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {

            SearchCard("Origin", Modifier.weight(1f))
            Image(
                painter = painterResource(id = R.drawable.right),
                contentDescription = "right",
                modifier = Modifier
                    .size(40.dp)
                    .padding(10.dp),
                alignment = Alignment.Center, alpha = 0.4f
            )
            SearchCard("Destination", Modifier.weight(1f))
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
                            selectedDate = LocalDate.now().plusDays(1)
                        }
                    )

                }

            }
        }
        SearchButton()
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
                                selectedDate = LocalDate.ofEpochDay(
                                    datePickerState.selectedDateMillis!! / 86400000
                                )
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
    }
}

@Composable
fun SearchButton() {
    Button(
        onClick = { /*TODO*/ },
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
fun SearchCard(text: String, weight: Modifier) {
    Card(
        border = BorderStroke(1.dp, BDarkWhite),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        modifier = weight
            .height(100.dp),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(top = 10.dp),
                color = BLightGrey,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Antalya",
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
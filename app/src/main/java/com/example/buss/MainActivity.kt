package com.example.buss

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buss.ui.theme.BLightBlue
import com.example.buss.ui.theme.BussTheme

class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<MyViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        WindowInsetsControllerCompat(window, window.decorView).apply {
            hide(WindowInsetsCompat.Type.statusBars())
            hide(WindowInsetsCompat.Type.navigationBars())
            systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }

        setContent {
            val pageControllerNav = rememberNavController()
            val bottomBarState = rememberSaveable { (mutableStateOf(false)) }

            BussTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    Scaffold(
                        bottomBar = {
                            AnimatedVisibility(visible = bottomBarState.value) {
                                BottomNavigationBar(
                                    navController = pageControllerNav,
                                    modifier = Modifier
                                        .background(BLightBlue)
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .padding(10.dp),
                                    viewModel = viewModel,
                                )
                            }
                        }
                    ) { paddingValues ->

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = paddingValues.calculateBottomPadding()),
                        ) {

                            NavHost(
                                navController = pageControllerNav,
                                startDestination = "loginPage"
                            ) {
                                composable("loginPage") {
                                    bottomBarState.value = false
                                    LoginPage(pageControllerNav, viewModel)
                                }
                                composable("homePage") {
                                    bottomBarState.value = true
                                    Home(pageControllerNav, viewModel)
                                }
                                composable("resultPage") {
                                    bottomBarState.value = true
                                    ResultPage(pageControllerNav, viewModel)
                                }
                                composable("citySelection") {
                                    bottomBarState.value = true

                                    CitySelection()
                                }
                                composable("provinces") {
                                    bottomBarState.value = true

                                    Provinces(viewModel, pageControllerNav)
                                }
                                composable("bus") {
                                    bottomBarState.value = true

                                    Bus(viewModel, pageControllerNav)
                                }
                                composable("provincePage") {
                                    bottomBarState.value = true

                                    ProvincePage(viewModel)
                                }
                                composable("busPage") {
                                    bottomBarState.value = true

                                    BusPage(viewModel)
                                }
                                composable("profile") {
                                    bottomBarState.value = true
                                    Profile(viewModel, pageControllerNav)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




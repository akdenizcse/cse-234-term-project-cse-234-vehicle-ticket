package com.example.buss

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buss.ui.theme.BBlackLight
import com.example.buss.ui.theme.BBlue
import com.example.buss.ui.theme.BLightGrey
import com.example.buss.ui.theme.BNeonDarkBlue
import com.example.buss.ui.theme.BussTheme
import java.time.LocalDate

class MainActivity : ComponentActivity() {

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
            var selectedDate by remember { mutableStateOf(LocalDate.now()) }
            BussTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    val pageControllerNav = rememberNavController()

                    val navController = rememberNavController()
                    NavHost(navController = pageControllerNav, startDestination = "loginPage") {
                        composable("loginPage") {
                            LoginPage(pageControllerNav)
                        }
                        composable("homePage") {
                            Home(pageControllerNav)
                        }
                        composable("resultPage") {
                            ResultPage(pageControllerNav)
                        }
                    }


                }
            }
        }
    }


}
@Composable
fun LoginPage( pageNav : NavHostController) {
    val navController = rememberNavController()
    Column {
        TopBox(navController)
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                Login(navController, pageNav)
            }
            composable("signup") {
                SignUp(navController, pageNav)
            }
        }
    }
}

@Composable
fun TopBox(navController: NavController) {
    Box(
        modifier = Modifier
            .background(BBlue)
            .height(200.dp)
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
            SignNavigation(navController)
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignNavigation(navController: NavController) {
    var signInColor by remember {
        mutableStateOf(BNeonDarkBlue)
    }
    var signUpColor by remember {
        mutableStateOf(BBlackLight)
    }
    val colors = TextFieldDefaults.colors(
        focusedIndicatorColor = BNeonDarkBlue,
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(35.dp, 35.dp, 0.dp, 0.dp))
            .background(Color.White),
        horizontalArrangement = Arrangement.Center
    ) {
        ClickableText(
            text = AnnotatedString("Sign In"),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                color = signInColor
            ),
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 20.dp,
                    end = 30.dp,
                    bottom = 10.dp
                ),
            onClick = {
                navController.navigate("login")
                signUpColor = BBlackLight
                signInColor = BNeonDarkBlue
            },
        )
        ClickableText(
            text = AnnotatedString("Sign Up"),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal,
                fontSize = 20.sp,
                color = signUpColor
            ),
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    start = 30.dp,
                    end = 20.dp,
                    bottom = 10.dp
                ),
            onClick = {
                navController.navigate("signup")
                signUpColor = BNeonDarkBlue
                signInColor = BBlackLight
            },
        )

    }
}




@Composable
fun Login(navController: NavController, pageControllerNav: NavHostController) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .navigationBarsPadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextInput(InputType.Name, keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()
        }))
        TextInput(InputType.Password, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            context.doLogin(pageControllerNav)
        }), focusRequester = passwordFocusRequester)
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            TextButton(modifier = Modifier.weight(1f),
                onClick = {

                }) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Continue without signing up.",
                        color = BLightGrey,
                        textAlign = TextAlign.Left
                    )
                }
            }
            TextButton(onClick = {}) {
                Text("Forgot Password?", color = BBlue, textAlign = TextAlign.Right)
            }
        }
        Button(
            onClick = {
                context.doLogin(pageControllerNav)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BNeonDarkBlue),
        ) {
            Text(
                "SIGN IN",
                Modifier.padding(vertical = 8.dp),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 16.sp
            )
        }
        Text(
            text = "Or sign in with",
            color = BBlackLight,
            modifier = Modifier.padding(top = 16.dp, end = 10.dp),
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Image(
                    painterResource(R.drawable.facebook),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(end = 10.dp)
                )
                Image(
                    painterResource(R.drawable.google),
                    contentDescription = "",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }

}

private fun Context.doLogin( pageControllerNav: NavHostController) {
    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
    pageControllerNav.navigate("homePage")
}

@Composable
fun SignUp(navController: NavController, pageControllerNav: NavHostController) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    Column(
        Modifier
            .navigationBarsPadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Bottom),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            null,
            Modifier.size(80.dp),
            tint = Color.White
        )
        TextInput(InputType.Name, keyboardActions = KeyboardActions(onNext = {
            passwordFocusRequester.requestFocus()
        }))
        TextInput(InputType.Password, keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            context.doLogin(pageControllerNav)
        }), focusRequester = passwordFocusRequester)
        Button(
            onClick = {
                context.doLogin(pageControllerNav)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BNeonDarkBlue),
        ) {
            Text("SIGN UP", Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold)
        }
        Divider(
            color = Color.White.copy(alpha = 0.3f),
            thickness = 1.dp,
            modifier = Modifier.padding(top = 48.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?", color = Color.White)
            TextButton(onClick = {}) {
                Text("SING UP")
            }
        }
    }

}

sealed class InputType(
    val label: String,
    val icon: ImageVector,
    val keyboardOptions: KeyboardOptions,
    val visualTransformation: VisualTransformation
) {
    object Name : InputType(
        label = "Username",
        icon = Icons.Default.Person,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        visualTransformation = VisualTransformation.None
    )

    object Password : InputType(
        label = "Password",
        icon = Icons.Default.Lock,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        visualTransformation = PasswordVisualTransformation()
    )
}

@Composable
fun TextInput(
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions
) {

    var value by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = { value = it },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .height(60.dp),
        //leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = inputType.label, fontWeight = FontWeight.Medium) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedLeadingIconColor = BLightGrey,
            unfocusedLeadingIconColor = BLightGrey,
            focusedLabelColor = BNeonDarkBlue,
            unfocusedLabelColor = BLightGrey,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = BNeonDarkBlue,
            unfocusedIndicatorColor = BLightGrey,
            unfocusedPlaceholderColor = BLightGrey,
            cursorColor = BNeonDarkBlue
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}



package com.example.buss

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.buss.ui.theme.BBlackLight
import com.example.buss.ui.theme.BBlue
import com.example.buss.ui.theme.BLightGrey
import com.example.buss.ui.theme.BNeonDarkBlue
import com.example.example.RegisterResponse
import kotlinx.coroutines.launch


@Composable
fun LoginPage(pageNav: NavHostController, viewModel: MyViewModel) {
    val navController = rememberNavController()
    Column {
        TopBox(navController)
        NavHost(navController = navController, startDestination = "login") {
            composable("login") {
                Login(navController, pageNav, viewModel)
            }
            composable("signup") {
                SignUp(navController, pageNav, viewModel)
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
fun Login(
    navController: NavController,
    pageControllerNav: NavHostController,
    viewModel: MyViewModel
) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    var emailBorderColor by remember { mutableStateOf(BNeonDarkBlue) }
    var passwordBorderColor by remember { mutableStateOf(BNeonDarkBlue) }




    Column(
        Modifier
            .navigationBarsPadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextInput(
            "Email",
            email.value,
            onValueChange = { email.value = it
                            emailBorderColor = if ( android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) BNeonDarkBlue else Color.Red},
            InputType.Name,
            keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }),
            borderColor = emailBorderColor
        )


        TextInput(
            "Password",
            password.value, onValueChange = { password.value = it
                                            passwordBorderColor = if (password.value.length >= 6 &&
                                password.value.any { it.isUpperCase() } &&
                                password.value.any { it.isDigit() }) BNeonDarkBlue else Color.Red},
            InputType.Password,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                coroutineScope.launch {
                    context.doLogin(pageControllerNav, email.value, password.value)
                }

            }),
            focusRequester = passwordFocusRequester,
            borderColor = passwordBorderColor
        )


        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            TextButton(modifier = Modifier.weight(1f),
                onClick = {
                    pageControllerNav.navigate("homePage")
                }) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "Continue without signing up.",
                        color = BLightGrey,
                        textAlign = TextAlign.Left,
                        fontSize = 13.sp
                    )
                }
            }
            TextButton(onClick = {}) {
                Text(
                    "Forgot Password?",
                    color = BBlue,
                    textAlign = TextAlign.Right,
                    fontSize = 13.sp
                )
            }
        }
        Button(
            onClick = {
                Log.d("Login", viewModel.authResponseLiveData.value.toString())
                if (viewModel.authResponseLiveData.value?.succeeded == true) {
                    pageControllerNav.navigate("homePage")
                }
                coroutineScope.launch {
                    context.doLogin(pageControllerNav, email.value, password.value)
                }

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

private suspend fun Context.doLogin(
    pageControllerNav: NavHostController,
    email: String,
    password: String
) {
    MyViewModel().authenticate(email, password, pageControllerNav)
//    Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
//    pageControllerNav.navigate("homePage")
}

@Composable
fun SignUp(
    navController: NavController,
    pageControllerNav: NavHostController,
    viewModel: MyViewModel
) {
    val context = LocalContext.current
    val passwordFocusRequester = FocusRequester()
    val focusManager = LocalFocusManager.current

    val coroutineScope = rememberCoroutineScope()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val username = remember { mutableStateOf("") }
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }


    var lastNameBorderColor by remember { mutableStateOf(BNeonDarkBlue) }
    var firstNameBorderColor by remember { mutableStateOf(BNeonDarkBlue) }
    var usernameBorderColor by remember { mutableStateOf(BNeonDarkBlue) }
    var emailBorderColor by remember { mutableStateOf(BNeonDarkBlue) }
    var passwordBorderColor by remember { mutableStateOf(BNeonDarkBlue) }

//
//    val isSignUpButtonEnabled = isPasswordValid && isEmailValid && isUsernameValid && isFirstNameValid && isLastNameValid
//
//    val signUpButtonColor = if (isSignUpButtonEnabled) BNeonDarkBlue else BBlackLight

    val response = viewModel._registerResponse.collectAsState()



    when (response.value) {
        is RegisterResponse -> {
            if (response.value.succeeded) {
                Log.d("Register", response.toString())

                Toast.makeText(context, "Registration successful. Please confirm your email adress!", Toast.LENGTH_LONG).show()
//                val browserIntent =
//                    Intent(Intent.ACTION_VIEW, Uri.parse(extractUrl(response.value.message!!)))
//
//                context.startActivity(browserIntent)
            }
        }
    }


    Column(
        Modifier
            .navigationBarsPadding()
            .padding(24.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            "First Name",
            firstName.value,
            onValueChange = {
                firstName.value = it
                firstNameBorderColor = if (firstName.value.length >= 6) BNeonDarkBlue else Color.Red
            },
            InputType.Name,
            keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }),
            borderColor = firstNameBorderColor
        )
        TextInput(
            "Last Name",
            lastName.value,
            onValueChange = { lastName.value = it
                            lastNameBorderColor = if (lastName.value.length >= 6) BNeonDarkBlue else Color.Red},
            InputType.Name,
            keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }),
            borderColor = lastNameBorderColor
        )
        TextInput(
            "Username",
            username.value,
            onValueChange = { username.value = it
                            usernameBorderColor = if ( username.value.length >= 6) BNeonDarkBlue else Color.Red},
            InputType.Name,
            keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }),
            borderColor = usernameBorderColor
        )
        TextInput(
            "Email",
            email.value,
            onValueChange = { email.value = it
                            emailBorderColor = if ( android.util.Patterns.EMAIL_ADDRESS.matcher(email.value).matches()) BNeonDarkBlue else Color.Red},
            InputType.Name,
            keyboardActions = KeyboardActions(onNext = {
                passwordFocusRequester.requestFocus()
            }),
            borderColor = emailBorderColor
        )
        TextInput(
            "Password", password.value,
            onValueChange = { password.value = it
                            passwordBorderColor = if (password.value.length >= 6 &&
                                password.value.any { it.isUpperCase() } &&
                                password.value.any { it.isDigit() }) BNeonDarkBlue else Color.Red},
            InputType.Password,
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus()
                coroutineScope.launch {
                    viewModel.registerUser(
                        firstName.value,
                        lastName.value,
                        email.value,
                        username.value,
                        password.value,
                        password.value
                    )

                }


            }),
            focusRequester = passwordFocusRequester,
            borderColor = passwordBorderColor
        )
        Button(
            onClick = {
                coroutineScope.launch {
                    viewModel.registerUser(
                        firstName.value,
                        lastName.value,
                        email.value,
                        username.value,
                        password.value,
                        password.value
                    )

                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = BNeonDarkBlue),
        ) {
            Text("SIGN UP", Modifier.padding(vertical = 8.dp), fontWeight = FontWeight.ExtraBold)
        }
    }

}

private fun extractUrl(message: String): String? {
    if (message == null) return null
    val prefix = "https://"
    val startIndex = message.indexOf(prefix)
    return if (startIndex != -1) {
        "https://" + message.substring(
            startIndex + prefix.length,
            message.length - 1
        )
    } else {
        null
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
    name: String,
    value: String,
    onValueChange: (String) -> Unit,
    inputType: InputType,
    focusRequester: FocusRequester? = null,
    keyboardActions: KeyboardActions,
    borderColor: Color = BLightGrey
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,

        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester ?: FocusRequester())
            .height(60.dp),
        //leadingIcon = { Icon(imageVector = inputType.icon, null) },
        label = { Text(text = name, fontWeight = FontWeight.Medium) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            focusedLeadingIconColor = BLightGrey,
            unfocusedLeadingIconColor = BLightGrey,
            focusedLabelColor = borderColor,
            unfocusedLabelColor = BLightGrey,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = borderColor,
            unfocusedIndicatorColor = BLightGrey,
            unfocusedPlaceholderColor = BLightGrey,
            cursorColor = BNeonDarkBlue,
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.Black
        ),
        singleLine = true,
        keyboardOptions = inputType.keyboardOptions,
        visualTransformation = inputType.visualTransformation,
        keyboardActions = keyboardActions
    )
}

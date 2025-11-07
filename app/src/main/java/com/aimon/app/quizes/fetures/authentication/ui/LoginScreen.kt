package com.aimon.app.quizes.fetures.authentication.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.ui.theme.QuizesTheme


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(

){
    LoginScreen()
}

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel= hiltViewModel()
){
    val context= LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.authState.collectLatest {
            if(it!=null){
                when(it){
                    is AuthState.Authenticated -> navController.navigate("home")
                    is AuthState.Error -> Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    else -> Unit
                }
            }
        }
}

    var mail by remember { mutableStateOf<String>("") }
    var pass by remember { mutableStateOf<String>("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.dark)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Login to Quizzie", color = Color.White, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = mail,
            onValueChange = {
                mail = it
            },
            label = {
                Text("Your Email")
            }
        )
        var visible by remember { mutableStateOf(false) }
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = pass,
            onValueChange = {
                pass = it
            },
            label = {
                Text("Your Password")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                viewModel.login(mail,pass)
            }
        ) {
            Text("Login", fontSize = 18.sp)
        }

        TextButton(
            onClick = {
                navController.navigate(route = "signup")


            }
        ) {
            Text("Don't Have an Account? Sign Up", color = Color.White)
        }
    }

}

@Preview
@Composable
fun ShowPreview(){
    QuizesTheme {
        Surface {
            var navController=rememberNavController()
            LoginScreen(navController = navController)
        }
    }
}
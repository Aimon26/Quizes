package com.aimon.app.quizes.fetures.authentication.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.ui.theme.QuizesTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignUpScreen(
){
    SignUpScreen()
}

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel= hiltViewModel()
){
    val context= LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.authState.collectLatest {
            if(it!=null){
                when(it){
                    is AuthState.Authenticated -> navController.navigate("login")
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
        Text("SignUp to Quizzie", color = Color.White, fontSize = 30.sp)
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = mail,
            onValueChange = {
                mail = it
            },
            label = {
                Text("Give Email")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        TextField(
            value = pass,
            onValueChange = {
                pass = it
            },
            label = {
                Text("Create Password")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            onClick = {
                viewModel.signUp(mail,pass)
            }
        ) {
            Text("Create an Account", fontSize = 18.sp)
        }

        TextButton(
            onClick = {
                navController.navigate(route = "login")


            }
        ) {
            Text("Already Have an Account? Login", color = Color.White)
        }
    }

}

@Preview
@Composable
fun ShowPreview2(){
    QuizesTheme {
        Surface {
            var navController= rememberNavController()
            SignUpScreen(navController)
        }
    }
}
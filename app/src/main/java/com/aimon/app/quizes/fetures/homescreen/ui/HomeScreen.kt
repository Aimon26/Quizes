package com.aimon.app.quizes.fetures.homescreen.ui

import android.provider.SyncStateContract
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.buildSpannedString
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.core.constants.Constants
import com.aimon.app.quizes.core.constants.EventHome
import com.aimon.app.quizes.core.constants.InitialEventHome
import com.aimon.app.quizes.fetures.authentication.ui.AuthState
import com.aimon.app.quizes.fetures.quizscreen.ui.QuizScreen
import com.aimon.app.quizes.ui.theme.QuizesTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel= hiltViewModel()
){
    val state by viewModel._homeState.collectAsState()
    val context= LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.authState.collectLatest {
            if(it!=null){
                when(it){
                    is AuthState.Unauthenticated->{navController.navigate("login")}
                    else -> Unit
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.dark))
            .padding(8.dp)
    ){
        Topbar(
            onSignOutClick = {
                viewModel.signOut()
            },
            onLeaderBoardClick = {
                navController.navigate("leaderBoard")
            }
        )
        Spacer(modifier = Modifier.height(30.dp))
        ExposedDropdownMenuExample(
            " Number of Quizzes",
            Constants.numberAsString,
            selectedText = state.NoOfQuiz.toString(),
            onDropDownClick = {viewModel.setNoOfQuiz(it)})
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenuExample(
            " Select Category",
            Constants.categories,
            selectedText = state.Category,
            onDropDownClick = {viewModel.setCategory(it)})
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenuExample(
            " Select Difficulty",
            Constants.difficulty,
            selectedText = state.Difficulty,
            onDropDownClick = {viewModel.setDifficulty(it)})
        Spacer(modifier = Modifier.height(20.dp))
        ExposedDropdownMenuExample(
            " Select Type",
            Constants.type,
            selectedText = state.Type,
            onDropDownClick = {viewModel.setType(it)})
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(50.dp),
            onClick = {
                Log.d("message","${state.NoOfQuiz}  ${state.Category}  ${state.Difficulty}  ${state.Type}")

                navController.navigate(route ="QuizScreen/${state.Category}/${state.NoOfQuiz}/${state.Difficulty}/${state.Type}")

            }
        ){
            Text("Generate Quiz", fontSize = 25.sp)

        }
    }
}

@Composable
fun Topbar(
    onSignOutClick:()-> Unit,
    onLeaderBoardClick:()-> Unit
){
    Column() {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .background(
                    color = colorResource(R.color.light),
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                ),
            contentAlignment = Alignment.Center

        ) {
            Column() {
                Row() {
                    Spacer(modifier = Modifier.width(270.dp))
                    TextButton(
                        modifier = Modifier
                            .size(height = 50.dp, width = 150.dp)
                            .background(
                                color = colorResource(R.color.light),
                                shape = RoundedCornerShape(3.dp)
                            ),
                        onClick = {
                           onLeaderBoardClick.invoke()


                        }

                    ) {
                        Text("Leaderboard", fontWeight = FontWeight.Bold, color = Color.White)

                    }
                }
                Row() {
                    Spacer(modifier = Modifier.width(290.dp))
                    TextButton(
                        modifier = Modifier
                            .size(height = 50.dp, width = 150.dp)

                            .background(
                                color = colorResource(R.color.light),
                                shape = RoundedCornerShape(3.dp)
                            ),
                        onClick = {
                            onSignOutClick.invoke()
                        }

                    ) {
                        Text("Sign Out", fontWeight = FontWeight.Bold, color = Color.White)

                    }
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp)
                ) {
                    Spacer(modifier = Modifier.width(130.dp))
                    Text("Quizzie", color = Color.White, fontSize = 40.sp)

                }
            }
        }
    }


}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuExample(
    menu: String,
    menuList: List<String>,
    selectedText:String,
    onDropDownClick:(String)-> Unit

) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(colorResource(R.color.light))
            .padding(5.dp)
    ) {
        Text(menu, fontSize = 20.sp, color = colorResource(R.color.white))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded },
            modifier = Modifier.fillMaxWidth()

        ) {
            TextField(
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor()
                    .fillMaxWidth()
                    .padding(start =20.dp ,top=5.dp,end=20.dp,bottom=10.dp)
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                menuList.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(item) },
                        onClick = {
                            onDropDownClick(item)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun showPreview(){
    QuizesTheme {
        Surface {
            Topbar(onSignOutClick = {}, onLeaderBoardClick = {})
        }
    }
}
package com.aimon.app.quizes.fetures.quizscreen.ui

import android.graphics.Paint
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.aimon.app.quizes.R
import com.aimon.app.quizes.core.constants.Constants
import com.aimon.app.quizes.fetures.homescreen.ui.HomeScreenViewModel
import com.aimon.app.quizes.fetures.quizscreen.domain.model.Question
import com.aimon.app.quizes.ui.theme.QuizesTheme
import javax.annotation.meta.When
import kotlin.String
import kotlin.collections.get


@Composable
fun QuizScreen(
    cat: String,
    no: Int,
    dif: String,
    type: String,
    onBackClick: () -> Unit,
    navController: NavController,
    viewModel: QuizScreeenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.light))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.dark))
                .height(60.dp)
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier

            ) {
                IconButton(
                    onClick = {
                        onBackClick.invoke()
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.outline_arrow_back_24),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(10.dp)
                            .background(color = Color.White)

                    )
                }
                Text(
                    "${cat}",
                    modifier = Modifier.padding(10.dp),
                    fontSize = 25.sp,
                    color = Color.White
                )

            }


        }
        Spacer(modifier = Modifier.height(10.dp))
        Column() {
            Row() {
                Text("  Number Of Quiz: ${no}", color = Color.White)
                Spacer(modifier = Modifier.width(90.dp))
                Text("Difficulty: ${dif}", color = Color.White)

            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .height(3.dp)
            ) {

            }
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (state.isLoading) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                } else if (state.isError != null) {
                    Text("${state.isError}")
                } else {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Row() {
                            Spacer(modifier = Modifier.width(20.dp))
                            Text(
                                "Quiz Number: ${state.crrnt + 1} out of ${state.quizes.size}",
                                fontSize = 20.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        QuizInterface(
                            question = state.quizes[state.crrnt],
                            modifier = Modifier,
                            onOptionSelected = {
                                viewModel.onOptionSelect(it)

                            }
                        )
                    }
                        Row(
                            modifier = Modifier
                                .padding(15.dp)

                        ) {
                            if(state.crrnt>0) {
                                Button(
                                    onClick = {
                                        viewModel.onPreviousClick()
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(60.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = Color.LightGray,
                                        contentColor = colorResource(R.color.dark)
                                    ),

                                    ) {
                                    Text("Previous", fontSize = 20.sp)

                                }
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            if(state.crrnt<state.quizes.size-1) {

                                Button(
                                    onClick = {
                                        viewModel.onNextClick()
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(60.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = colorResource(R.color.orange),
                                        contentColor = colorResource(R.color.dark)
                                    ),
                                ) {
                                    Text("Next", fontSize = 20.sp)
                                }
                            }
                            if(state.crrnt==state.quizes.size-1){
                                Button(
                                    onClick = {
                                        navController.navigate(route ="ScoreScreen/${state.score}/${state.quizes.size}")
                                    },
                                    modifier = Modifier
                                        .weight(1F)
                                        .height(60.dp),
                                    colors = ButtonDefaults.filledTonalButtonColors(
                                        containerColor = colorResource(R.color.orange),
                                        contentColor = colorResource(R.color.dark)
                                    ),
                                ) {
                                    Text("Submit", fontSize = 20.sp)
                                }
                            }
                            }

                        }
                    }

                }

            }
        }



@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    val navController = rememberNavController()

    QuizScreen(
        cat = "General Knowledge",
        no = 10,
        dif = "Medium",
        type = "Multiple Choice",
        onBackClick = { },
        navController = navController
    )
}

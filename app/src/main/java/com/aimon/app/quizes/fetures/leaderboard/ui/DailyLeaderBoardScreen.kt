package com.aimon.app.quizes.fetures.leaderboard.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.window.embedding.EmbeddingBounds
import com.aimon.app.quizes.R
import com.aimon.app.quizes.ui.theme.QuizesTheme

@Composable
fun DailyLeaderBoardScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.dark))
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(R.color.teal_700))
                .size(50.dp),
            contentAlignment = Alignment.Center

        ){
            Text("Daily Leaderboard", fontWeight = FontWeight.Bold, color = Color.Black, fontSize = 25.sp)

        }




    }
}

@Preview
@Composable
fun ShowPreview(){
    QuizesTheme {
        Surface {
            DailyLeaderBoardScreen()
        }
    }
}
package com.aimon.app.quizes.fetures.quizscreen.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aimon.app.quizes.fetures.quizscreen.domain.model.Question
import com.aimon.app.quizes.ui.theme.QuizesTheme

@Composable
fun QuizInterface(
    modifier: Modifier = Modifier,
    question: Question,
    onOptionSelected: (String) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2C3E50)
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        ) {
            // Question Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Question Text
            Text(
                text = question.question,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Options
            question.options.forEachIndexed { index, option ->
                val isWrong = if(question.selectedAnswer != null && question.selectedAnswer != question.correctAnswer && question.selectedAnswer == option) true else false
                val isCorrect = if(question.selectedAnswer != null && question.selectedAnswer == question.correctAnswer && question.selectedAnswer == option) true else false

                OptionItem(
                    label = ('A' + index).toString(),
                    text = option,
                    isSelected = question.selectedAnswer==option ,
                    isCorrect = isCorrect,
                    isWrong = isWrong,
                    onClick = {
                        onOptionSelected(option)
                    }
                )

                if (index < question.options.size - 1) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
private fun OptionItem(
    label: String,
    text: String,
    isSelected: Boolean,
    isCorrect: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        isCorrect -> Color(0xFF27AE60)
        isWrong -> Color(0xFFE74C3C)
        isSelected -> Color(0xFFFF9F43)
        else -> Color(0xFF34495E)
    }

    val borderColor = when {
        isCorrect -> Color(0xFF27AE60)
        isWrong -> Color(0xFFE74C3C)
        isSelected -> Color(0xFFFF9F43)
        else -> Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(backgroundColor, RoundedCornerShape(12.dp))
            .border(
                width = if (isSelected || isCorrect || isWrong) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Label Circle
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(
                    color = if (isSelected || isCorrect || isWrong) Color.White.copy(alpha = 0.3f)
                    else Color.White.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.weight(1f)
        )
        if (isCorrect) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Correct",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        } else if (isWrong) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Wrong",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview
@Composable
fun QuizInterfaceShow() {
    QuizesTheme {
        Surface {
            var selectedOption by remember { mutableStateOf<String?>(null) }
            var showResult by remember { mutableStateOf(false) }

            val question =
                "Which space agency has decided to carry out first all-female spacewalk at the international space station (iss)?"
            val options = listOf("Roscosmos", "NASA", "ISRO", "JAXA")
            val correctAnswer = "NASA"

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1A252F))
            ) {
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
//                    QuizInterface(
//                        questionNumber = 1,
//                        totalQuestions = 60,
//                        question = question,
//                        options = options,
//                        selectedOption = selectedOption,
//                        onOptionSelected = { selectedOption = it },
//                        correctAnswer = correctAnswer,
//                        showResult = showResult
//                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Submit Button
                    Button(
                        onClick = { showResult = !showResult },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9F43)
                        ),
                        shape = RoundedCornerShape(12.dp),
                        enabled = selectedOption != null
                    ) {
                        Text(
                            text = if (showResult) "NEXT" else "SUBMIT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
package com.aimon.app.quizes.fetures.quizscreen.domain.model

data class Question(
    val question: String,
    val options:List<String>,
    val correctAnswer:String,
    val selectedAnswer:String?=null
)
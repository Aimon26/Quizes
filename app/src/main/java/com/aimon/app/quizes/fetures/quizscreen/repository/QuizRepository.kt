package com.aimon.app.quizes.fetures.quizscreen.repository

import com.aimon.app.quizes.fetures.quizscreen.data.network.dto.QuizDto
import com.aimon.app.quizes.fetures.quizscreen.domain.model.Question
import okio.Options

interface QuizRepository {
    suspend fun getQuiz(
       amount:Int,
       category: Int,
       difficulty:String,
       type: String
    ): List<Question>
}
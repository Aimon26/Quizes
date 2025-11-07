package com.aimon.app.quizes.fetures.quizscreen.repository

import com.aimon.app.quizes.fetures.quizscreen.data.network.dto.QuizApiService
import com.aimon.app.quizes.fetures.quizscreen.data.network.dto.QuizDto
import com.aimon.app.quizes.fetures.quizscreen.domain.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuizRepositoryImpl @Inject constructor(
    private val quizApiService: QuizApiService
) : QuizRepository{
    override suspend fun getQuiz(
        amount: Int,
        category: Int,
        difficulty: String,
        type: String
    ): List<Question> {
        return withContext(Dispatchers.IO) {
            val response= quizApiService.getQuiz(amount, category, difficulty, type)
            return@withContext response.results.map{
                Question(
                    question = it.question,
                    options = it.incorrect_answers.toMutableList().apply {
                        add(it.correct_answer)
                    }.shuffled(),
                    correctAnswer = it.correct_answer,
                    selectedAnswer = null
                )
            }

        }
    }
}
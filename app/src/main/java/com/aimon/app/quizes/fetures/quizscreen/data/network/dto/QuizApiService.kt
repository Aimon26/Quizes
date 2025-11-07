package com.aimon.app.quizes.fetures.quizscreen.data.network.dto

import retrofit2.http.GET
import retrofit2.http.Query

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount")amount:Int,
        @Query("category")category: Int,
        @Query("difficulty")difficulty: String,
        @Query("type")type:String
    ): QuizDto

}
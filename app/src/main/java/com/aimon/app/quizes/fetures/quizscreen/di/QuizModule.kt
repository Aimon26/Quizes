package com.aimon.app.quizes.fetures.quizscreen.di


import com.aimon.app.quizes.fetures.quizscreen.data.network.dto.QuizApiService
import com.aimon.app.quizes.fetures.quizscreen.repository.QuizRepository
import com.aimon.app.quizes.fetures.quizscreen.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface QuizModule {

    @Binds
    @Singleton
    fun provideQuizApiServiceImpl(repository: QuizRepositoryImpl): QuizRepository

    companion object {
        @Provides
        @Singleton
        fun provideQuizApiService(retrofit: Retrofit): QuizApiService = retrofit.create(
            QuizApiService::class.java)
    }

}
package com.aimon.app.quizes.fetures.leaderboard.di

import com.aimon.app.quizes.fetures.leaderboard.repository.LeaderBoardRepository
import com.aimon.app.quizes.fetures.leaderboard.repository.LeaderBoardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface LeaderBoardModule{

    @Binds
    @Singleton
    fun provideLeaderBoardRepositoryImpl(repository: LeaderBoardRepositoryImpl): LeaderBoardRepository
}

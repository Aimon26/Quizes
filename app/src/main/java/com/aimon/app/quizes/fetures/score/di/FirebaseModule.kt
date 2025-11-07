package com.aimon.app.quizes.fetures.score.di
import com.aimon.app.quizes.fetures.score.repository.FireBaseRepository
import com.aimon.app.quizes.fetures.score.repository.FireBaseRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface FirebaseModule {

    @Binds
    @Singleton
    fun provideFirebaseRepositoryImpl(repository: FireBaseRepositoryImpl): FireBaseRepository
}

package com.aimon.app.quizes.core.di

import android.content.Context
import com.aimon.app.quizes.core.constants.NetworkConstant
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    // Remove BuildConfig, just return the interceptor in debug builds
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        @ApplicationContext app: Context,
    ) = OkHttpClient.Builder()
        .apply {
            addInterceptor(httpLoggingInterceptor)
            addInterceptor(ChuckerInterceptor.Builder(app).build())

            connectTimeout(NetworkConstant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NetworkConstant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NetworkConstant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        }
        .build()

    @Provides
    @Singleton
    fun providesRetrofitClient(
        client: OkHttpClient
    ): Retrofit = Retrofit.Builder()
        .baseUrl(NetworkConstant.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
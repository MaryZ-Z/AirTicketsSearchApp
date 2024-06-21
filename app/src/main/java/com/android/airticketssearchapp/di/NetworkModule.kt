package com.android.airticketssearchapp.di

import android.content.Context
import com.android.airticketssearchapp.BuildConfig.BASE_URL
import com.android.airticketssearchapp.restapi.AirTicketsSearchService
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClientBuilder(
        @ApplicationContext context: Context
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            addInterceptor(ChuckerInterceptor.Builder(context).build())
            connectTimeout(15, TimeUnit.SECONDS)
            readTimeout(20, TimeUnit.SECONDS)
            writeTimeout(15, TimeUnit.SECONDS)
        }

    @Singleton
    @Provides
    fun provideAirTicketsSearchService(
        moshi: Moshi,
        builder: OkHttpClient.Builder
    ): AirTicketsSearchService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(builder.build())
            .build().create(AirTicketsSearchService::class.java)
}
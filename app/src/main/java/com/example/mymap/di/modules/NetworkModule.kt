package com.example.mymap.di.modules

import com.example.mymap.bases.BASE_URL
import com.squareup.moshi.Moshi
import com.example.mymap.network.api.ApiService
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.logging.Level
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {


    @InstallIn(SingletonComponent::class)
    @Module
    object NetworkModules {

        @Provides
        @Singleton
        fun provideKotlinJsonAdapterFactory(): KotlinJsonAdapterFactory = KotlinJsonAdapterFactory()

        @Provides
        @Singleton
        fun provideMoshi(kotlinJsonAdapterFactory: KotlinJsonAdapterFactory): Moshi =
            Moshi.Builder()
                .add(kotlinJsonAdapterFactory)
                .build()

        @Provides
        @Singleton
        fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory =
            MoshiConverterFactory.create(moshi)

        @Provides
        @Singleton
        fun provideOkHttp(
            loggingInterceptor: LoggingInterceptor,
            authInterceptor: AuthInterceptor
        ): OkHttpClient =
            OkHttpClient
                .Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(authInterceptor)
                .build()


        @Provides
        @Singleton
        fun provideInterceptor() = LoggingInterceptor.Builder()
            .setLevel(Level.BASIC)
            .log(Platform.INFO)
            .request("Request")
            .response("Response")
            .build()


        @Provides
        @Singleton
        fun provideRetrofitClient(
            okHttp: OkHttpClient,
            moshiConverterFactory: MoshiConverterFactory
        ): Retrofit = Retrofit.Builder()
            .addConverterFactory(moshiConverterFactory)
            .client(okHttp)
            .baseUrl(BASE_URL)
            .build()


        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)
    }

}
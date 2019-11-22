package ru.chieffly.memoscope.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.chieffly.memoscope.net.AuthApi
import ru.chieffly.memoscope.net.MemApi
//import ru.chieffly.meetroom.net.AuthApi
//import ru.chieffly.meetroom.net.MeetroomApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {

    @Provides
    @Singleton
    fun provideGson(): Gson =
        GsonBuilder().create()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://demo3161256.mockable.io/") //MEMS
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideMemApi (retrofit: Retrofit) : MemApi =
        retrofit.create(MemApi::class.java)

    @Provides
    @Singleton
    fun provideAuthApi (retrofit: Retrofit) : AuthApi =
        retrofit.create(AuthApi::class.java)
}
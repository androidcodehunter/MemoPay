package com.memo.pay.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.memo.pay.api.MemoPayService
import com.memo.pay.api.MemoPayService.Companion.BASE_URL
import com.memo.pay.api.MemoPayService.Companion.DATE_FORMAT
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val netWorkModule = module {
    factory { provideGson() }
    factory { provideApiService(get()) }
    factory { provideOkHttpClient() }
    single { provideRetrofit(get(), get()) }
}

fun provideGson(): Gson{
    return GsonBuilder().setDateFormat(DATE_FORMAT).create()
}

fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder().baseUrl(BASE_URL).client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson)).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().build()
}

fun provideApiService(retrofit: Retrofit): MemoPayService = retrofit.create(MemoPayService::class.java)

package com.wd.tech.utli

import java.util.concurrent.TimeUnit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * author: Daijianming
 * data: 2019/12/20 13:13:37
 * function：单例
 */
class Netutli private constructor() {

    val baseApi: BaseApi

    private object singleHoder {
        val INSTANCE = Netutli()
    }

    init {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val ok = OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://mobile.bwstudent.com/")
            .client(ok)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        baseApi = retrofit.create(BaseApi::class.java)
    }

    companion object {

        val instance: Netutli
            get() = singleHoder.INSTANCE
    }
}

package com.latihanbyrg.tugaspertemuan12.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    val BASE_API = "https://api.thecatapi.com/v1/"
    val XAPIKEY = "live_OdbZOAKalfhCVNRxALi3mz9XjIxIcdj6TQOF9gIUfkQHjbJsnuchlof7H7uWxU8z"

    fun getInstance() : ApiService {
        val mOkHttpInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)

        val mOkHttpClient = OkHttpClient.Builder()
            .addInterceptor(mOkHttpInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .addHeader("x-api-key", XAPIKEY)
                    .method(original.method, original.body)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()

        val builder = Retrofit.Builder()
            .baseUrl(BASE_API)
            .client(mOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return builder.create(ApiService::class.java)


    }


}
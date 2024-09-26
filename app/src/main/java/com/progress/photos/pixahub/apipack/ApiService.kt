package com.progress.photos.pixahub.apipack

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

     @GET("api")

     suspend fun getImages(
        @Query("key") apikey : String,
        @Query("q") query : String,
        @Query("image_type") image : String = "photo"
        ) : ImageResponse


}

private const val BASE_URL = "https://pixabay.com"

private val api = Retrofit.Builder().baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val pixaBayService: ApiService = api.create(ApiService::class.java)

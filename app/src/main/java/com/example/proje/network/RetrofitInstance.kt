package com.example.proje.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://kasimadalan.pe.hu/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val foodApi: FoodApi by lazy {
        retrofit.create(FoodApi::class.java)
    }

    val cartApi: CartApi by lazy {
        retrofit.create(CartApi::class.java)
    }


}
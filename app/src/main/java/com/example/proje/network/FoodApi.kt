package com.example.proje.network

import com.example.proje.model.FoodResponse
import retrofit2.http.GET

interface FoodApi {
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun getAllFoods(): FoodResponse





}

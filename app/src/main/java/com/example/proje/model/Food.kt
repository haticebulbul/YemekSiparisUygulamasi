package com.example.proje.model

data class Food(
    val yemek_id: String,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: String
)

data class FoodResponse(
    val yemekler: List<Food>,
    val success: Int
)
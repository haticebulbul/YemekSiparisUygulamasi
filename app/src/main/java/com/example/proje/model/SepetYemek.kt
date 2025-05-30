package com.example.proje.model

data class SepetYemek(
    val sepet_yemek_id: Int,
    val yemek_adi: String,
    val yemek_resim_adi: String,
    val yemek_fiyat: Int,
    val yemek_siparis_adet: Int,
    val kullanici_adi: String
)
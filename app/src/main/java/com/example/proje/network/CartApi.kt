package com.example.proje.network

import com.example.proje.model.CrudCevap
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import com.example.proje.model.SepetYemekResponse

interface CartApi {
    @FormUrlEncoded
    @POST("yemekler/sepeteYemekEkle.php")
    fun sepeteYemekEkle(
        @Field("yemek_adi") yemekAdi: String,
        @Field("yemek_resim_adi") yemekResimAdi: String,
        @Field("yemek_fiyat") yemekFiyat: Int,
        @Field("yemek_siparis_adet") yemekSiparisAdet: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CrudCevap>



    @FormUrlEncoded
    @POST("yemekler/sepettekiYemekleriGetir.php")
    fun sepetiGetir(
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<SepetYemekResponse>


    @FormUrlEncoded
    @POST("yemekler/sepettenYemekSil.php")
    fun sepettenYemekSil(
        @Field("sepet_yemek_id") sepetYemekId: Int,
        @Field("kullanici_adi") kullaniciAdi: String
    ): Call<CrudCevap>


}
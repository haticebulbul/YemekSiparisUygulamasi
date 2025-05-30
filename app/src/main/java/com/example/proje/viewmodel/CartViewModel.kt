package com.example.proje.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.proje.model.CrudCevap
import com.example.proje.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.proje.model.SepetYemek
import com.example.proje.model.SepetYemekResponse


class CartViewModel : ViewModel() {


    fun sepeteYemekEkle(
        yemekAdi: String,
        yemekResimAdi: String,
        yemekFiyat: Int,
        yemekSiparisAdet: Int,
        kullaniciAdi: String,
        onComplete: (success: Boolean, message: String) -> Unit
    ) {
        RetrofitInstance.cartApi.sepeteYemekEkle(
            yemekAdi, yemekResimAdi, yemekFiyat, yemekSiparisAdet, kullaniciAdi
        ).enqueue(object : Callback<CrudCevap> {
            override fun onResponse(call: Call<CrudCevap>, response: Response<CrudCevap>) {
                if (response.isSuccessful && response.body()?.success == 1) {
                    Log.d("SepetEkle", "Başarılı: ${response.body()?.message}")
                    onComplete(true, response.body()?.message ?: "Başarılı")
                } else {
                    Log.e("SepetEkle", "Başarısız: ${response.code()} ${response.message()}")
                    onComplete(false, "Sepete ekleme başarısız")
                }
            }


            override fun onFailure(call: Call<CrudCevap>, t: Throwable) {
                onComplete(false, "Hata: ${t.message}")
            }
        })
    }

    private val _sepetList = MutableLiveData<List<SepetYemek>>()
    val sepetList: LiveData<List<SepetYemek>> = _sepetList

    private val cartApi = RetrofitInstance.cartApi // Retrofit API instance'ını al

    fun sepetiGetir(kullaniciAdi: String) {
        Log.d("SepetGetirDebug", "Sepet verisi çekiliyor -> kullanıcı: $kullaniciAdi")

        cartApi.sepetiGetir(kullaniciAdi).enqueue(object : Callback<SepetYemekResponse> {
            override fun onResponse(call: Call<SepetYemekResponse>, response: Response<SepetYemekResponse>) {
                if (response.isSuccessful) {
                    val sepetYemekler = response.body()?.sepet_yemekler ?: emptyList()
                    Log.d("CartViewModel", "Gelen sepet verisi: ${sepetYemekler.size} adet ürün")

                    sepetYemekler.forEach {
                        Log.d("SepetGetirDebug", "Yemek: ${it.yemek_adi}, Kullanıcı: ${it.kullanici_adi}")
                    }

                    _sepetList.value = sepetYemekler
                } else {
                    Log.e("CartViewModel", "Sepet verisi alınamadı, code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SepetYemekResponse>, t: Throwable) {
                Log.e("CartViewModel", "Sepet verisi alınamadı, hata: ${t.message}")
            }
        })
    }
    fun yemekSil(sepetYemekId: Int, kullaniciAdi: String, onResult: (Boolean) -> Unit) {
        RetrofitInstance.cartApi.sepettenYemekSil(sepetYemekId, kullaniciAdi)
            .enqueue(object : Callback<CrudCevap> {
                override fun onResponse(call: Call<CrudCevap>, response: Response<CrudCevap>) {
                    if (response.isSuccessful && response.body()?.success == 1) {
                        onResult(true)
                        sepetiGetir(kullaniciAdi)
                    } else {
                        onResult(false)
                    }
                }

                override fun onFailure(call: Call<CrudCevap>, t: Throwable) {
                    onResult(false)
                }
            })
    }


}

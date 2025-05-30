package com.example.proje.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proje.model.Food
import com.example.proje.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel : ViewModel() {

    private val _foods = MutableStateFlow<List<Food>>(emptyList())
    val foods: StateFlow<List<Food>> = _foods

    init {
        getAllFoods()
    }

    private fun getAllFoods() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.foodApi .getAllFoods()
                Log.e("FoodViewModel", "Gelen veri: ${response.yemekler.size}")
                _foods.value = response.yemekler
            } catch (e: Exception) {
                Log.e("FoodViewModel", "HATA: ${e.localizedMessage}")
            }
        }
    }
}

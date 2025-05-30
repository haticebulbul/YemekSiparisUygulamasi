package com.example.proje.view.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proje.R
import com.example.proje.databinding.FragmentHome2Binding
import com.example.proje.model.Food
import com.example.proje.view.adapter.FoodAdapter
import com.example.proje.viewmodel.FoodViewModel
import kotlinx.coroutines.flow.collectLatest

class HomeFragment : Fragment() {

    private var _binding: FragmentHome2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: FoodViewModel by viewModels()
    private lateinit var foodAdapter: FoodAdapter
    private var originalList: List<Food> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHome2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCart.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
        foodAdapter = FoodAdapter { selectedFood ->
            val bundle = Bundle().apply {
                putString("name", selectedFood.yemek_adi)
                putString("price", selectedFood.yemek_fiyat)
                putString("imageUrl", "http://kasimadalan.pe.hu/yemekler/resimler/${selectedFood.yemek_resim_adi}")
            }
            view.findNavController().navigate(R.id.action_homeFragment_to_foodDetailFragment, bundle)
        }

        binding.recyclerViewFoods.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewFoods.adapter = foodAdapter

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.foods.collectLatest { foods ->
                originalList = foods
                foodAdapter.updateList(foods)
            }
        }

        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().lowercase()
                val filtered = originalList.filter { food ->
                    food.yemek_adi.lowercase().contains(query)
                }
                foodAdapter.updateList(filtered)
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        binding.btnAnaYemek.setOnClickListener {
            filterByCategory("Ana Yemek")
        }

        binding.btnIcecek.setOnClickListener {
            filterByCategory("İçecek")
        }

        binding.btnTatli.setOnClickListener {
            filterByCategory("Tatlı")
        }
        binding.btnTumYemekler.setOnClickListener {
            foodAdapter.updateList(originalList)  // Tüm yemekleri göster
        }
    }

    private fun filterByCategory(category: String) {
        val filteredList = originalList.filter { food ->
            when (category) {
                "Ana Yemek" -> food.yemek_adi in listOf("Izgara Somon", "Izgara Tavuk", "Köfte", "Lazanya", "Makarna", "Pizza")
                "İçecek" -> food.yemek_adi in listOf("Ayran", "Fanta", "Kahve", "Su")
                "Tatlı" -> food.yemek_adi in listOf("Baklava", "Kadayıf", "Sütlaç", "Tiramisu")
                else -> true
            }
        }
        foodAdapter.updateList(filteredList)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.proje.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.proje.databinding.FragmentFoodDetailBinding
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.proje.viewmodel.CartViewModel
import com.example.proje.R


class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private val kullaniciAdi = "hatice_bulbul"
    private val cartViewModel: CartViewModel by activityViewModels()
    private var siparisAdedi = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("name") ?: ""
        val price = arguments?.getString("price")?.toDoubleOrNull() ?: 0.0
        val imageUrl = arguments?.getString("imageUrl") ?: ""

        binding.tvDetailName.text = name
        binding.tvDetailPrice.text = "${price} ₺"
        binding.tvQuantity.text = siparisAdedi.toString()

        binding.btnIncrease.setOnClickListener {
            siparisAdedi++
            binding.tvQuantity.text = siparisAdedi.toString()
        }
        binding.btnDecrease.setOnClickListener {
            if (siparisAdedi > 1) {
                siparisAdedi--
                binding.tvQuantity.text = siparisAdedi.toString()
            }
        }
        Glide.with(this)
            .load(imageUrl)
            .into(binding.imgDetail)
        binding.btnAddToCart.setOnClickListener {
            cartViewModel.sepeteYemekEkle(
                yemekAdi = name,
                yemekResimAdi = imageUrl.substringAfterLast('/'),
                yemekFiyat = price.toInt(),
                yemekSiparisAdet = siparisAdedi,
                kullaniciAdi = kullaniciAdi
            ) { success, message ->
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                if (success) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        cartViewModel.sepetiGetir(kullaniciAdi)
                        findNavController().navigate(R.id.action_foodDetailFragment_to_cartFragment)
                    }, 1500)
                }
            }
        }
        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
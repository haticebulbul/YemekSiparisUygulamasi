package com.example.proje.view.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proje.databinding.FragmentCartBinding
import com.example.proje.view.adapter.CartAdapter
import com.example.proje.viewmodel.CartViewModel


class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!


    private val cartViewModel: CartViewModel by activityViewModels()
    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        adapter = CartAdapter { yemek ->
            cartViewModel.yemekSil(yemek.sepet_yemek_id, "hatice_bulbul") { success ->
                if (success) {
                    Log.d("Silmeİşlemi", "Silme başarılı")
                    cartViewModel.sepetiGetir("hatice_bulbul")
                } else {
                    Log.e("Silmeİşlemi", "Silme başarısız")
                }
            }
        }

        binding.rvCartItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvCartItems.adapter = adapter

        cartViewModel.sepetList.observe(viewLifecycleOwner) { yemekler ->
            Log.d("SepetKontrol", "Sepetteki ürün sayısı: ${yemekler.size}")
            adapter.submitList(yemekler)

            val toplamFiyat = yemekler.sumOf {
                it.yemek_fiyat.toInt() * it.yemek_siparis_adet.toInt()
            }


            binding.tvTotal.text = "$toplamFiyat ₺"
        }


        cartViewModel.sepetiGetir("hatice_bulbul")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}

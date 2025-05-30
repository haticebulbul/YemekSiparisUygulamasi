package com.example.proje.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.proje.databinding.ItemCartBinding
import com.example.proje.model.SepetYemek

class CartAdapter(
    private val onDeleteClick: (SepetYemek) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var yemekListesi = listOf<SepetYemek>()

    fun submitList(yeniListe: List<SepetYemek>) {
        yemekListesi = yeniListe
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = yemekListesi.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(yemekListesi[position])
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(yemek: SepetYemek) {
            binding.tvYemekAdi.text = yemek.yemek_adi
            binding.tvYemekAdet.text = "Adet: ${yemek.yemek_siparis_adet}"

            val toplamFiyat = yemek.yemek_fiyat.toInt() * yemek.yemek_siparis_adet.toInt()
            binding.tvYemekFiyat.text = "$toplamFiyat ₺"

            Glide.with(binding.root.context)
                .load("http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}")
                .into(binding.imgYemek)


            binding.btnDelete.setOnClickListener {
                onDeleteClick(yemek)
            }
        }
    }
}


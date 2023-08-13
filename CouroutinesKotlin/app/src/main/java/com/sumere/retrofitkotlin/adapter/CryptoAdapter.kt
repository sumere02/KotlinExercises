package com.sumere.retrofitkotlin.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sumere.retrofitkotlin.databinding.RecyclerRowBinding
import com.sumere.retrofitkotlin.model.CryptoModel

class CryptoAdapter(val crpytoList: List<CryptoModel>,val listener: Listener): RecyclerView.Adapter<CryptoAdapter.CryptoHolder>() {

    private val colors: Array<String> = arrayOf("#13bd27","#29c1e1","#b129e1","#d3df13","#f6bd0c","#a1fb93","#0d9de3","#ffe48f")
    interface Listener{
        fun onItemClick(cryptoModel: CryptoModel){
        }
    }

    class CryptoHolder(val binding: RecyclerRowBinding): ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoAdapter.CryptoHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CryptoHolder(binding)
    }

    override fun onBindViewHolder(holder: CryptoAdapter.CryptoHolder, position: Int) {
        holder.binding.recyclerRowCryptoCodeTextView.text = crpytoList[position].currency
        holder.binding.recyclerRowCryptoValueTextView.text = crpytoList[position].price
        holder.binding.root.setBackgroundColor(Color.parseColor(colors[position%8]))
        holder.binding.root.setOnClickListener{
            listener.onItemClick(crpytoList[position])
        }
    }

    override fun getItemCount(): Int {
        return crpytoList.size
    }

}
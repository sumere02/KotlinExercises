package com.sumere.artbookfragments.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.sumere.artbookfragments.databinding.RecyclerRowBinding
import com.sumere.artbookfragments.model.Art
import com.sumere.artbookfragments.view.MainPageFragment
import com.sumere.artbookfragments.view.MainPageFragmentDirections

class ArtAdapter(val artList: List<Art>): RecyclerView.Adapter<ArtAdapter.ArtHolder>() {

    class ArtHolder(val binding: RecyclerRowBinding):ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtAdapter.ArtHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtAdapter.ArtHolder, position: Int) {
        holder.binding.recyclerViewRowTextView.text = artList[position].artName
        holder.binding.recyclerViewRowTextView.setOnClickListener {
            val action = MainPageFragmentDirections.actionMainPageFragmentToArtDetailsFragment(false,artList[position])
            Navigation.findNavController(holder.itemView).navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return artList.size
    }
}
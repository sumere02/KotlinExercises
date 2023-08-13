package com.sumere.artbook

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumere.artbook.databinding.RecyclerRowBinding

class ArtAdapter(val artArrayList: ArrayList<Art>): RecyclerView.Adapter<ArtAdapter.ArtRowViewHolder>() {

    class ArtRowViewHolder(val binding: RecyclerRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtRowViewHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArtRowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artArrayList.size
    }

    override fun onBindViewHolder(holder: ArtRowViewHolder, position: Int) {
        holder.binding.recyclerViewRowView.text = artArrayList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            intent.putExtra("info", "old")
            intent.putExtra("id",artArrayList[position].id)
            holder.itemView.context.startActivity(intent)
        }
    }

}
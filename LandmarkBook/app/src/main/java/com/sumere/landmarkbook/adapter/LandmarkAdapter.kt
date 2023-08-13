package com.sumere.landmarkbook.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sumere.landmarkbook.databinding.RecyclerListViewRowBinding
import com.sumere.landmarkbook.model.Landmark
import com.sumere.landmarkbook.model.mySingleton
import com.sumere.landmarkbook.view.DetailsActivity

class LandmarkAdapter(private val landmarkList: ArrayList<Landmark>): RecyclerView.Adapter<LandmarkAdapter.LandmarkHolder>(){

    class LandmarkHolder(val binding: RecyclerListViewRowBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LandmarkHolder {
        val binding = RecyclerListViewRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LandmarkHolder(binding)
    }

    override fun getItemCount(): Int {
        return landmarkList.size
    }

    override fun onBindViewHolder(holder: LandmarkHolder, position: Int) {
        holder.binding.recylerListViewRow.text = landmarkList.get(position).name
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context,DetailsActivity::class.java)
            //selectedLandmark = landmarkList.get(position)
            //intent.putExtra("landmark",landmarkList.get(position))
            mySingleton.chosenLandmark = landmarkList.get(position)
            holder.itemView.context.startActivity(intent)
        }
    }
}
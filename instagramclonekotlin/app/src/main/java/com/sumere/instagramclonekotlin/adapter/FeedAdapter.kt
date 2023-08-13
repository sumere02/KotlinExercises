package com.sumere.instagramclonekotlin.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import com.sumere.instagramclonekotlin.databinding.RecyclerRowBinding
import com.sumere.instagramclonekotlin.model.Post

class FeedAdapter(val postList: ArrayList<Post>): RecyclerView.Adapter<FeedAdapter.PostHolder>() {
    class PostHolder(val binding: RecyclerRowBinding) : ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedAdapter.PostHolder {
        val binding = RecyclerRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PostHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedAdapter.PostHolder, position: Int) {
        holder.binding.recyclerRowViewEmailView.text = postList[position].email
        holder.binding.recyclerRowViewCommentView.text = postList[position].comment
        print(postList.get(position).downloadURL)
        Picasso.get().load(postList.get(position).downloadURL).into(holder.binding.recyclerRowViewImageView)
    }

    override fun getItemCount(): Int {
        return postList.size
    }

}
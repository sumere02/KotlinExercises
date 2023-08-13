package com.sumere.instagramclonekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.sumere.instagramclonekotlin.R
import com.sumere.instagramclonekotlin.adapter.FeedAdapter
import com.sumere.instagramclonekotlin.databinding.ActivityFeedBinding
import com.sumere.instagramclonekotlin.model.Post

class FeedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private var postArrayList: ArrayList<Post> = ArrayList()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(this@FeedActivity.layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        db = Firebase.firestore
        getData()
        binding.recyclerViewFeedActivity.layoutManager = LinearLayoutManager(this@FeedActivity)
        feedAdapter = FeedAdapter(postArrayList)
        binding.recyclerViewFeedActivity.adapter = feedAdapter
    }

    private fun getData(){
        db.collection("Posts").orderBy("date",Query.Direction.DESCENDING)
            .addSnapshotListener{value,error ->
            if(error != null){
                Toast.makeText(this@FeedActivity,error.localizedMessage,Toast.LENGTH_LONG).show()
            } else{
                if(value != null){
                    if(!value.isEmpty){
                        postArrayList.clear()
                        val documents = value.documents
                        for(document in documents){
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadURL = document.get("downloadURL") as String
                            val post = Post(userEmail,comment,downloadURL)
                            postArrayList.add(post)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.feedactivitymenu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.feedActivityMenuSignOutItemView){
            auth.signOut()
            val intent = Intent(this@FeedActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        else if(item.itemId == R.id.feedActivityMenuAddPostItemView){
            val intent = Intent(this@FeedActivity, UploadActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}
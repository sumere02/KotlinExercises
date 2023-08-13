package com.sumere.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.sumere.instagramclonejava.R;
import com.sumere.instagramclonejava.adapter.PostAdapter;
import com.sumere.instagramclonejava.databinding.ActivityFeedBinding;
import com.sumere.instagramclonejava.model.Post;

import java.util.ArrayList;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {
    private ActivityFeedBinding binding;
    private Intent intent;
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    private ArrayList<Post> postArrayList;
    private PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postArrayList = new ArrayList<>();
        getData();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(postArrayList);
        binding.recyclerView.setAdapter(postAdapter);

    }

    private void getData() {
        firebaseFirestore.collection("Posts").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                     if(error != null){
                         Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                     }
                     if(value != null) {
                         Post post;
                        for(DocumentSnapshot snapshot : value.getDocuments()){
                            Map<String,Object> data = snapshot.getData();
                            String userEmail = (String)data.get("useremail");
                            String comment = (String)data.get("comment");
                            String downloadURL = (String)data.get("downloadURL");
                            post = new Post(userEmail,comment,downloadURL);
                            postArrayList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                     }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_addpost){
            //Upload post
            Intent intent = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.menu_signout){
            //Sign Out
            auth.signOut();
            intent = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
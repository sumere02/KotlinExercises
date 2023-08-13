package com.sumere.instagramclonekotlin.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sumere.instagramclonekotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(this@MainActivity.layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        val currentUser = auth.currentUser
        if(currentUser != null){
            val intent = Intent(this@MainActivity, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun signInButtonClicked(view: View){
        val email = binding.emailTextViewMainActivity.text.toString()
        val password = binding.passwordTextViewMainActivity.text.toString()
        if(!(email.equals("") || password.equals(""))){
            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_LONG).show()
        }
    }

    fun signUpButtonClicked(view: View){
        val email = binding.emailTextViewMainActivity.text.toString()
        val password = binding.passwordTextViewMainActivity.text.toString()
        if(!(email.equals("") || password.equals(""))) {
            auth.createUserWithEmailAndPassword(email,password)
                .addOnSuccessListener {
                    val intent = Intent(this@MainActivity, FeedActivity::class.java)
                    startActivity(intent)
                    finish()
                }.addOnFailureListener {
                    Toast.makeText(this@MainActivity,it.localizedMessage,Toast.LENGTH_LONG).show()
                }
        } else {
            Toast.makeText(this,"Invalid Email or Password",Toast.LENGTH_LONG).show()
        }
    }
}
package com.sumere.alertdialog

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 1) Toast 2) Alert Dialog 3) Snackbar
        // Context = Information about application
    }

    fun save(view : View){
        //Toast.makeText(this@MainActivity,"Are You Sure?",Toast.LENGTH_LONG).show()
        val alert = AlertDialog.Builder(this@MainActivity)
        alert.setTitle("Save")
        alert.setMessage("Are You Sure?")
        alert.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1: Int) {
                Toast.makeText(this@MainActivity,"Saved!",Toast.LENGTH_LONG).show()
            }
        })
        alert.setNegativeButton("No",object:DialogInterface.OnClickListener{
            override fun onClick(p0: DialogInterface?, p1:Int){
                Toast.makeText(this@MainActivity,"Not Saved",Toast.LENGTH_LONG).show()
            }
        })
        alert.show()
    }
}
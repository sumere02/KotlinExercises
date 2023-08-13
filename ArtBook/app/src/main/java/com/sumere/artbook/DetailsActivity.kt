package com.sumere.artbook

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.sumere.artbook.databinding.ActivityDetailsBinding
import java.io.ByteArrayOutputStream

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private var selectedImageBitmap: Bitmap? = null
    private lateinit var database: SQLiteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(this.layoutInflater)
        setContentView(binding.root)
        registerLauncher()
        database = this.openOrCreateDatabase("Arts", MODE_PRIVATE,null)
        val intent = intent
        val info = intent.getStringExtra("info")
        if(info.equals("new")){
            binding.artNameTextViewDetailsActivity.setText("")
            binding.artistNameTextViewDetailsActivity.setText("")
            binding.yearTextViewDerailsActivity.setText("")
            binding.selectImageViewDetailsActivity.setImageResource(R.drawable.selectimage)
            binding.saveButtonViewDetailsActivity.visibility = View.VISIBLE
        } else {
            binding.saveButtonViewDetailsActivity.visibility = View.INVISIBLE
            val selectedId = intent.getIntExtra("id",0)
            println(selectedId)
            val cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?", arrayOf(selectedId.toString()))
            val artnameIx = cursor.getColumnIndex("artname")
            val artistNameIx = cursor.getColumnIndex("artistname")
            val yearIx = cursor.getColumnIndex("year")
            val imageIx = cursor.getColumnIndex("image")
            while (cursor.moveToNext()){
                binding.artNameTextViewDetailsActivity.setText(cursor.getString(artnameIx))
                binding.artistNameTextViewDetailsActivity.setText(cursor.getString(artistNameIx))
                binding.yearTextViewDerailsActivity.setText(cursor.getString(yearIx))
                val byteArray = cursor.getBlob(imageIx)
                val bitmap = BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                binding.selectImageViewDetailsActivity.setImageBitmap(bitmap)
            }
            cursor.close()
        }
    }

    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result ->
                if(result.resultCode == RESULT_OK){
                    val intentFromResult = result.data
                    if(intentFromResult != null){
                        val imageURI = intentFromResult.data
                        //binding.selectImageViewDetailsActivity.setImageURI(imageURI)
                        try {
                            if(imageURI != null && Build.VERSION.SDK_INT >= 28) {
                                val source = ImageDecoder.createSource(this@DetailsActivity.contentResolver, imageURI)
                                selectedImageBitmap = ImageDecoder.decodeBitmap(source)
                                binding.selectImageViewDetailsActivity.setImageBitmap(selectedImageBitmap)
                            } else{
                                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this@DetailsActivity.contentResolver,imageURI)
                                binding.selectImageViewDetailsActivity.setImageBitmap(selectedImageBitmap)
                            }
                        } catch (e: Exception){
                            e.printStackTrace()
                        }
                    }
                }
            })

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {result ->
                if(result){
                    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    Toast.makeText(this@DetailsActivity,"Permission Needed",Toast.LENGTH_LONG).show()
                }
            })
    }

    fun makeSmallerBitmap(image: Bitmap,maximumSize: Int): Bitmap{
        var width = image.width
        var height = image.height
        val bitmapRatio: Double = width.toDouble() / height.toDouble()
        if(bitmapRatio > 1){
            //Landscape
            width = maximumSize
            val scaledHeight = width / bitmapRatio
            height = scaledHeight.toInt()
        } else{
            //Portrait
            height = maximumSize
            val scaledWidth = height * bitmapRatio
            width = scaledWidth.toInt()
        }
        return Bitmap.createScaledBitmap(image,width,height,true)
    }

    fun saveImage(view: View){
        val smallBitmap: Bitmap
        val outputStream: ByteArrayOutputStream = ByteArrayOutputStream()
        var byteArray: ByteArray? = null
        val artName = binding.artNameTextViewDetailsActivity.text.toString()
        val artistName = binding.artistNameTextViewDetailsActivity.text.toString()
        val year = binding.yearTextViewDerailsActivity.text.toString()
        if(selectedImageBitmap != null){
            smallBitmap = makeSmallerBitmap(selectedImageBitmap!!,300)
            smallBitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream)
            byteArray = outputStream.toByteArray()
        }
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY,artname VARCHAR,artistname VARCHAR,year VARCHAR,image BLOB)")
            val sqlString = "INSERT INTO arts (artname, artistname, year, image) VALUES(?,?,?,?)"
            val statement = database.compileStatement(sqlString)
            statement.bindString(1,artName)
            statement.bindString(2,artistName)
            statement.bindString(3,year)
            statement.bindBlob(4, byteArray)
            statement.execute()

        }catch (e: java.lang.Exception){
            e.printStackTrace()
        }
        finish()
    }

    fun selectImage(view: View){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if(ContextCompat.checkSelfPermission(this@DetailsActivity,Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                //Permission Denied
                if(ActivityCompat.shouldShowRequestPermissionRationale(this@DetailsActivity,Manifest.permission.READ_MEDIA_IMAGES)){
                    //Request Permission - Rationale
                    Snackbar.make(view,"Permission Needed For Gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }).show()

                } else {
                    //Request Permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            } else {
                //Permission Accepted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        } else {
            if(ContextCompat.checkSelfPermission(this@DetailsActivity,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                //Permission Denied
                if(ActivityCompat.shouldShowRequestPermissionRationale(this@DetailsActivity,Manifest.permission.READ_EXTERNAL_STORAGE)){
                    //Request Permission - Rationale
                    Snackbar.make(view,"Permission Needed For Gallery",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener {
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }).show()

                } else {
                    //Request Permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            } else {
                //Permission Accepted
                val intentToGallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intentToGallery)
            }
        }
    }
}
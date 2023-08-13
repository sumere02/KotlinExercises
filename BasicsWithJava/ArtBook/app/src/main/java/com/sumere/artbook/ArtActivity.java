package com.sumere.artbook;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.sumere.artbook.databinding.ActivityArtBinding;

import java.io.ByteArrayOutputStream;

public class ArtActivity extends AppCompatActivity {

    private ActivityArtBinding binding;
    private Bitmap selectedImage;
    SQLiteDatabase database;
    ActivityResultLauncher<Intent> activityResultLauncher;
    ActivityResultLauncher<String> permissionLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityArtBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        registerLauncher();
        Intent intent = getIntent();
        String info = intent.getStringExtra("info");
        database = this.openOrCreateDatabase("Arts",MODE_PRIVATE,null);
        if(info.matches("new")){
            //new Art
            binding.artNameText.setText("");
            binding.artistNameText.setText("");
            binding.yearText.setText("");
            binding.selectImageView.setImageResource(R.drawable.selectimage);
            binding.saveButton.setVisibility(View.VISIBLE);
        }
        else{
            //old art
            int artId = intent.getIntExtra("artId",0);
            try {
                Cursor cursor = database.rawQuery("SELECT * FROM arts WHERE id = ?",new String[]{String.valueOf(artId)});
                int artNameIx = cursor.getColumnIndex("artname");
                int artistIx = cursor.getColumnIndex("paintername");
                int yearIx = cursor.getColumnIndex("year");
                int imageIx = cursor.getColumnIndex("image");
                while(cursor.moveToNext()){
                    binding.artNameText.setText(cursor.getString(artNameIx));
                    binding.artistNameText.setText(cursor.getString(artistIx));
                    binding.yearText.setText(cursor.getString(yearIx));
                    byte[] imageByteArray = cursor.getBlob(imageIx);
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray,0,imageByteArray.length);
                    binding.selectImageView.setImageBitmap(bitmap);
                }
                cursor.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            binding.saveButton.setVisibility(View.INVISIBLE);
        }
    }


    public void registerLauncher(){
        permissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    //Permission Granted
                    Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    activityResultLauncher.launch(intentToGallery);
                }
                else{
                    //Permission Denied
                    Toast.makeText(ArtActivity.this,"Permission Needed", Toast.LENGTH_LONG).show();
                }
            }
        });

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK){
                    Intent intentFromResult = result.getData();
                    if(intentFromResult != null){
                        Uri imageDataUri = intentFromResult.getData();
                        try{
                            if(Build.VERSION.SDK_INT >= 28) {
                                ImageDecoder.Source source = ImageDecoder.createSource(ArtActivity.this.getContentResolver(), imageDataUri);
                                selectedImage = ImageDecoder.decodeBitmap(source);
                            }
                            else{
                                selectedImage = MediaStore.Images.Media.getBitmap(ArtActivity.this.getContentResolver(),imageDataUri);
                            }
                            binding.selectImageView.setImageBitmap(selectedImage);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{

                }
            }
        });

    }

    public Bitmap makeSmallerImage(Bitmap image,int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if(bitmapRatio > 1){
            //landscape image
            width = maxSize;
            height = (int)(width / bitmapRatio);
        }
        else{
            //portrait image
            height = maxSize;
            width = (int)(height * bitmapRatio);
        }
        return image.createScaledBitmap(image,width,height,true);
    }

    public void save(View view){
        String artName = binding.artNameText.getText().toString();
        String artistName = binding.artistNameText.getText().toString();
        String year = binding.yearText.getText().toString();
        Bitmap smallImage = makeSmallerImage(selectedImage,300);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG,50,outputStream);
        byte[] imageByteArray = outputStream.toByteArray();
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS arts(id INTEGER PRIMARY KEY,artname VARCHAR,paintername VARCHAR,year VARCHAR,image BLOB)");
            String sqlString = "INSERT INTO arts (artname,paintername,year,image) VALUES (?,?,?,?)";
            SQLiteStatement sqLiteStatement = database.compileStatement(sqlString);
            sqLiteStatement.bindString(1,artName);
            sqLiteStatement.bindString(2,artistName);
            sqLiteStatement.bindString(3,year);
            sqLiteStatement.bindBlob(4,imageByteArray);
            sqLiteStatement.execute();
        } catch (Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(ArtActivity.this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void selectImage(View view){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            //Android >= 33
            if (ContextCompat.checkSelfPermission(ArtActivity.this, Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ArtActivity.this, Manifest.permission.READ_MEDIA_IMAGES)) {
                    Snackbar.make(view, "Permission required", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Request Permission
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);
                        }
                    }).show();

                } else {
                    //Request Permission
                    permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES);

                }
            } else {
                //Permission Granted
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
        else {
            if (ContextCompat.checkSelfPermission(ArtActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(ArtActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Snackbar.make(view, "Permission required", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            //Request Permission
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);
                        }
                    }).show();

                } else {
                    //Request Permission
                    permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE);

                }
            } else {
                //Permission Granted
                Intent intentToGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                activityResultLauncher.launch(intentToGallery);
            }
        }
    }
}
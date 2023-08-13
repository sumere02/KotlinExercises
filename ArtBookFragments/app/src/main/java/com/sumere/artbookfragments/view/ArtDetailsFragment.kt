package com.sumere.artbookfragments.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.Navigation
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import com.sumere.artbookfragments.R
import com.sumere.artbookfragments.databinding.FragmentArtDetailsBinding
import com.sumere.artbookfragments.model.Art
import com.sumere.artbookfragments.roomdb.ArtDao
import com.sumere.artbookfragments.roomdb.ArtDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.io.ByteArrayOutputStream
import java.lang.Exception

class ArtDetailsFragment : Fragment() {

    private lateinit var binding: FragmentArtDetailsBinding
    private var info:Boolean? = null
    private var art:Art? = null
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var selectedImageBitmap:Bitmap
    private lateinit var db: ArtDatabase
    private lateinit var artDao: ArtDao
    private var compositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLaunchers()
        db = Room.databaseBuilder(requireContext(), ArtDatabase::class.java,"Arts").build()
        artDao = db.artDao()
    }

    private fun registerLaunchers(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if(result.resultCode == AppCompatActivity.RESULT_OK){
                    val intentFromResult = result.data
                    if(intentFromResult != null){
                        val imageURI = intentFromResult.data
                        try {
                            if(imageURI != null && Build.VERSION.SDK_INT >= 28){
                                val source = ImageDecoder.createSource(requireActivity().contentResolver,imageURI)
                                selectedImageBitmap = ImageDecoder.decodeBitmap(source)
                                binding.selectImageViewDetailsFragment.setImageBitmap(selectedImageBitmap)
                            } else{
                                selectedImageBitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,imageURI)
                                binding.selectImageViewDetailsFragment.setImageBitmap(selectedImageBitmap)
                            }
                        }catch (e: Exception){
                            Toast.makeText(requireActivity(),e.localizedMessage,Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })

        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {result ->
                if(result){
                    val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                }else{
                    Toast.makeText(requireActivity(),"Permission Needed",Toast.LENGTH_LONG).show()
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtDetailsBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            info = ArtDetailsFragmentArgs.fromBundle(it).info
            art = ArtDetailsFragmentArgs.fromBundle(it).art
        }
        //New Art is Adding
        if(info!!){
            binding.deleteButtonViewDetailsFragment.visibility = View.GONE
            binding.saveButtonViewDetailsFragment.visibility = View.VISIBLE
            binding.selectImageViewDetailsFragment.setOnClickListener{
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
                    if(ContextCompat.checkSelfPermission(view.context,Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED){
                        //Request Permission
                        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_MEDIA_IMAGES)){
                            Snackbar.make(view,"Permission Needed",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener{
                                permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            }).show()
                        }else{
                            permissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        }
                    }else{
                        //Permission Granted
                        val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        activityResultLauncher.launch(intentToGallery)
                    }
                }else{
                    if(ContextCompat.checkSelfPermission(view.context,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        //Request Permission
                        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),Manifest.permission.READ_EXTERNAL_STORAGE)){
                            Snackbar.make(view,"Permission Needed",Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",View.OnClickListener{
                                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }).show()
                        }else{
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    }else{
                        //Permission Granted
                        val intentToGallery = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                        activityResultLauncher.launch(intentToGallery)
                    }
                }

            }
            binding.saveButtonViewDetailsFragment.setOnClickListener{
                val smallBitmap: Bitmap
                val outputStream:ByteArrayOutputStream = ByteArrayOutputStream()
                var byteArray: ByteArray? = null
                val artName = binding.artNameTextViewDetailsFragment.text.toString()
                val artistName = binding.artistNameTextViewDetailsFragment.text.toString()
                val year = binding.yearTextViewDetailsFragment.text.toString()
                if(selectedImageBitmap != null){
                    smallBitmap = makeSmallerBitmap(selectedImageBitmap!!,300)
                    smallBitmap.compress(Bitmap.CompressFormat.JPEG,50,outputStream)
                    byteArray = outputStream.toByteArray()
                }
                val art = Art(artName,artistName,year,byteArray!!)
                compositeDisposable.add(artDao.insert(art)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::handleResponse)
                )
            }
        }else{
            //Old Art
            binding.deleteButtonViewDetailsFragment.visibility = View.VISIBLE
            binding.saveButtonViewDetailsFragment.visibility = View.GONE
            art?.let {art ->
                binding.artNameTextViewDetailsFragment.setText(art.artName)
                binding.artistNameTextViewDetailsFragment.setText(art.artistName)
                binding.yearTextViewDetailsFragment.setText(art.year)
                val bitmap = BitmapFactory.decodeByteArray(art.image,0,art.image.size)
                binding.selectImageViewDetailsFragment.setImageBitmap(bitmap)
                binding.deleteButtonViewDetailsFragment.setOnClickListener {
                    compositeDisposable.add(artDao.delete(art).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResponse))
                }
            }
        }
    }
    private fun makeSmallerBitmap(image: Bitmap,maximumSize: Int): Bitmap{
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

    private fun handleResponse(){
        val action = ArtDetailsFragmentDirections.actionArtDetailsFragmentToMainPageFragment()
        Navigation.findNavController(requireActivity(),R.id.fragmentContainerViewMainActivity).navigate(action)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
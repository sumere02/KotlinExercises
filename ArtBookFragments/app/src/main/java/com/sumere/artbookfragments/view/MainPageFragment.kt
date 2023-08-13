package com.sumere.artbookfragments.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sumere.artbookfragments.R
import com.sumere.artbookfragments.adapter.ArtAdapter
import com.sumere.artbookfragments.databinding.FragmentMainPageBinding
import com.sumere.artbookfragments.model.Art
import com.sumere.artbookfragments.roomdb.ArtDao
import com.sumere.artbookfragments.roomdb.ArtDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainPageFragment : Fragment() {

    private lateinit var binding: FragmentMainPageBinding
    private lateinit var artAdapter: ArtAdapter
    private lateinit var artList: ArrayList<Art>
    private var compositeDisposable = CompositeDisposable()
    private lateinit var db: ArtDatabase
    private lateinit var artDao: ArtDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(requireContext(),ArtDatabase::class.java,"Arts").build()
        artDao = db.artDao()

        compositeDisposable.add(artDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::handleResponse))

    }
    fun handleResponse(artList: List<Art>){
        artAdapter = ArtAdapter(artList)
        binding.recyclerViewMainFragment.adapter = artAdapter
        binding.recyclerViewMainFragment.layoutManager = LinearLayoutManager(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainPageBinding.inflate(inflater,container,false)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

}
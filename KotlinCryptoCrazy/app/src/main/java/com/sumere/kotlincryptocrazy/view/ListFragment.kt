package com.sumere.kotlincryptocrazy.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sumere.kotlincryptocrazy.databinding.FragmentListBinding
import com.sumere.kotlincryptocrazy.model.CryptoModel
import com.sumere.kotlincryptocrazy.service.CryptoAPI
import com.sumere.kotlincryptocrazy.viewmodel.CryptoViewModel
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : Fragment(), RecyclerViewAdapter.Listener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!
    private var cryptoAdapter = RecyclerViewAdapter(arrayListOf(),this)
    private val viewModel by viewModel<CryptoViewModel>()

    //private val api = get<CryptoAPI>()
    //private val apiLazy by inject<CryptoAPI>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layout = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layout
        //viewModel = ViewModelProvider(this).get(CryptoViewModel::class.java)
        viewModel.getDataFromAPI()
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.cryptoList.observe(viewLifecycleOwner,Observer{ cryptos->
            binding.recyclerView.visibility = View.VISIBLE
            cryptoAdapter = RecyclerViewAdapter(ArrayList(cryptos.data ?: arrayListOf()),this)
            binding.recyclerView.adapter = cryptoAdapter
        })
        viewModel.cryptoError.observe(viewLifecycleOwner, Observer {
            if(it.data == true){
                binding.recyclerView.visibility = View.GONE
                binding.cryptoErrorText.visibility = View.VISIBLE
                binding.cryptoProgressBar.visibility = View.GONE
            } else{
                binding.cryptoErrorText.visibility = View.GONE
            }
        })
        viewModel.cryptoLoading.observe(viewLifecycleOwner, Observer {
            if(it.data == true){
                binding.recyclerView.visibility = View.GONE
                binding.cryptoErrorText.visibility = View.GONE
                binding.cryptoProgressBar.visibility = View.VISIBLE
            } else{
                binding.cryptoProgressBar.visibility = View.GONE
            }
        })
    }


    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(requireContext(),"Clicked On: ${cryptoModel.currency}",Toast.LENGTH_LONG).show()
    }

}
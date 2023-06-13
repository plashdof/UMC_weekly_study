package com.a9week.search

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.a9week.databinding.FragmentSearchBinding
import com.a9week.search.adapter.SearchAdapter
import com.a9week.search.models.SearchData
import com.a9week.search.network.SearchAPI
import com.a9week.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val BASE_URL = "https://openapi.naver.com/"
    private val TAG = "debugging"
    private var searchText = ""
    private val naverClientSecret = "3kPMsLc_pe"
    private val naverClientId = "vzCwg8Cgd6VZkaSvxHXC"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER)){
                binding.etSearch.clearFocus()
                searchText = "영화 " + binding.etSearch.text.toString()
                startSearch()

                // 키보드 내리기
                val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            return@setOnKeyListener false
        }



    }
    private fun startSearch(){

        RetrofitInterface(BASE_URL).getInstance().create(SearchAPI::class.java)
            .searchImage(naverClientId,naverClientSecret,searchText,30).enqueue(object: Callback<SearchData> {
                override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                    Log.d(TAG,response.raw().toString())
                    Log.d(TAG,response.body()?.items.toString())
                    if (!response.body()?.toString().isNullOrBlank()){
                        val adapter =
                            response.body()?.items?.let { SearchAdapter(requireContext(), it) }
                        binding.recycler.adapter = adapter
                        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
                    }
                }

                override fun onFailure(call: Call<SearchData>, t: Throwable) {
                    Log.d(TAG,t.message.toString())                }
            })

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
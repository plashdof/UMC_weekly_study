package com.a9week.search

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.a9week.App
import com.a9week.HomeActivity
import com.a9week.databinding.FragmentSearchBinding
import com.a9week.search.adapter.SearchAdapter
import com.a9week.search.models.SearchData
import com.a9week.search.network.SearchAPI
import com.a9week.util.DisplayUtil
import com.a9week.util.LoadingDialog
import com.a9week.util.RetrofitInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt

class SearchFragment : Fragment() {
    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val BASE_URL = "https://openapi.naver.com/"
    private val TAG = "debugging"
    private var searchText = "범죄도시"
    private val naverClientSecret = "3kPMsLc_pe"
    private val naverClientId = "vzCwg8Cgd6VZkaSvxHXC"



    val dialog = LoadingDialog()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startSearch()

        dialog.show(parentFragmentManager,"asd")

        binding.etSearch.setOnKeyListener { v, keyCode, event ->

            if ((keyCode == KeyEvent.KEYCODE_ENTER)){
                dialog.show(parentFragmentManager,"asd")
                binding.etSearch.clearFocus()
                searchText = "영화 " + binding.etSearch.text.toString()
                startSearch()

                // 키보드 내리기
                val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            return@setOnKeyListener false
        }

        binding.etSearch.setOnFocusChangeListener{ view, hasFocus ->
            val activity  =  activity as HomeActivity
            if(hasFocus){
                activity.btnHide()
            }else{
                activity.btnShow()
            }
        }


    }
    private fun startSearch(){

        RetrofitInterface(BASE_URL).getInstance().create(SearchAPI::class.java)
            .searchImage(naverClientId,naverClientSecret,searchText,100).enqueue(object: Callback<SearchData> {
                override fun onResponse(call: Call<SearchData>, response: Response<SearchData>) {
                    Log.d(TAG,response.raw().toString())
                    Log.d(TAG,response.body()?.items.toString())
                    if (!response.body()?.toString().isNullOrBlank()){
                        val adapter =
                            response.body()?.items?.let { SearchAdapter(requireContext(), it) }
                        binding.recycler.adapter = adapter
                        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
                    }
                    dialog.dismiss()
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
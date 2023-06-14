package com.a9week.movie

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.a9week.App
import com.a9week.util.RetrofitInterface
import com.a9week.databinding.FragmentMovieBinding
import com.a9week.movie.adapter.MovieAdapter
import com.a9week.movie.network.MovieAPI
import com.a9week.movie.models.MovieData
import com.a9week.util.LoadingDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieFragment : Fragment() {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!
    private val MovieKEY = "f7625c8604721c1f15ffcf29228d617e"
    private val TAG = "debugging"
    private val BASE_URL = "https://www.kobis.or.kr/kobisopenapi/webservice/rest/"
    val dialog = LoadingDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog.show(parentFragmentManager,"123")

        RetrofitInterface(BASE_URL).getInstance().create(MovieAPI::class.java)
            .getBoxoffice(MovieKEY,"20230612","K").enqueue(object: Callback<MovieData> {
                override fun onResponse(call: Call<MovieData>, response: Response<MovieData>) {
                    binding.tvTitle.text = response.body()?.boxOfficeResult?.boxofficeType
                    binding.tvDate.text = response.body()?.boxOfficeResult?.showRange

                    val adapter = response.body()?.boxOfficeResult?.dailyBoxOfficeList?.let {
                        MovieAdapter(requireContext(),
                            it
                        )
                    }
                    binding.recycler.adapter = adapter
                    binding.recycler.layoutManager = LinearLayoutManager(context)
                    dialog.dismiss()
                }

                override fun onFailure(call: Call<MovieData>, t: Throwable) {
                    Log.d(TAG,t.message.toString())                }
            })


    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
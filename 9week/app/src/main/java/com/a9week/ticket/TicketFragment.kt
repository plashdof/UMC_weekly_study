package com.a9week.ticket

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.a9week.databinding.FragmentTicketBinding
import com.a9week.ticket.models.LocationData
import com.a9week.ticket.network.LocationAPI
import com.a9week.util.RetrofitInterface
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TicketFragment : Fragment(){

    private var _binding : FragmentTicketBinding? = null
    private val binding get() = _binding!!
    private val TAG = "debugging"
    private val BASE_URL = "https://dapi.kakao.com/"
    private val API_KEY = "KakaoAK 4803b7316e4e6e02fd2c075831d1602a"
    private lateinit var markerArr: Array<MapPOIItem?>
    private var searchText = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        markerArr = arrayOf()

        val marker = MapPOIItem()
        val mp = MapPoint.mapPointWithGeoCoord(36.321655, 127.378953)
        marker.itemName = "Default Marker"
        marker.tag = 0
        marker.mapPoint = mp
        marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
        binding.mapView.addPOIItem(marker)

        // 줌 레벨 변경
        binding.mapView.setZoomLevel(4, true);
        binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading;

        binding.btnMyLoc.setOnClickListener {
            startTracking()
        }

        binding.etSearch.setOnKeyListener { v, keyCode, event ->
            if ((keyCode == KeyEvent.KEYCODE_ENTER)){
                binding.etSearch.clearFocus()
                searchText = binding.etSearch.text.toString()
                startSearch()

                // 키보드 내리기
                val imm: InputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(view.windowToken, 0)
            }
            return@setOnKeyListener false
        }
    }


    // 위치추적 시작
    private fun startTracking() {
        Log.d(TAG,"debugging")
        binding.mapView.currentLocationTrackingMode = MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading
    }

    private fun startSearch(){
        RetrofitInterface(BASE_URL).getInstance().create(LocationAPI::class.java)
            .getSearchKeyword(API_KEY,searchText).enqueue(object : Callback<LocationData>{
                override fun onResponse(
                    call: Call<LocationData>,
                    response: Response<LocationData>
                ) {
                    Log.d(TAG,response.raw().toString())
                    Log.d(TAG,response.body().toString())

                    if(!response.body().toString().isBlank()){
                        for( i in response.body()!!.documents){
                            val marker = MapPOIItem()
                            marker.mapPoint =
                                MapPoint.mapPointWithGeoCoord(
                                    i.x.toDouble(),
                                    i.y.toDouble()
                                )
                            marker.itemName = i.place_name
                            markerArr = markerArr.plus(marker)
                        }
                        binding.mapView.addPOIItems(markerArr)
                    }

                }
                override fun onFailure(call: Call<LocationData>, t: Throwable) {}
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
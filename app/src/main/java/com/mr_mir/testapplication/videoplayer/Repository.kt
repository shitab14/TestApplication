package com.mr_mir.testapplication.videoplayer

import com.mr_mir.testapplication.retrofitsetup.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Shitab Mir on 22,July,2020
 */

object LoanClaimHistoryRepository {

    fun getData(path: String?, callback: VideoPlayerViewModel.DataCallback) {

        APIService.getApiService().getData(path)
            ?.enqueue(object : Callback<VideoAPIResponse> {
                override fun onResponse(call: Call<VideoAPIResponse>, response: Response<VideoAPIResponse>) {
                    if (response.isSuccessful) {
                        callback.setData(Resource.success(response.body()))
                    } else{
                        callback.setData(Resource.error(response.errorBody()?.string() ?: "Failed to get data."))
                    }
                }
                override fun onFailure(call: Call<VideoAPIResponse>, t: Throwable) {
                    callback.setData(Resource.error(t.localizedMessage ?: "Failed to get data."))
                }

            })
    }

}
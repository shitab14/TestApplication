package com.mr_mir.testapplication.retrofitsetup

import com.mr_mir.testapplication.videoplayer.VideoAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Shitab Mir on 22,July,2020
 */

interface APIEndpoints {

    @GET("/jsons/{path_var}/data.json")
    fun getData(@Path("path_var") pathVar: String?): Call<VideoAPIResponse>?

}
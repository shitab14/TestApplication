package com.mr_mir.testapplication.retrofitsetup

/**
 * Created by Shitab Mir on 22,July,2020
 */
class APIService {
    companion object {

        private const val BASE_URL: String = "https://shitab14.github.io"
        fun getApiService(): APIEndpoints {
            return RetrofitBuilder.getClient(BASE_URL)!!.create(APIEndpoints::class.java)
        }
    }
}
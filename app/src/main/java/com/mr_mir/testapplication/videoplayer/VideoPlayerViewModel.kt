package com.mr_mir.testapplication.videoplayer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Shitab Mir on 22,July,2020
 */
class VideoPlayerViewModel : ViewModel() {

    var mLiveData = MutableLiveData<Resource<VideoAPIResponse?>>()

    fun fetchData( path: String?) {
        LoanClaimHistoryRepository.getData(path, object : DataCallback {
            override fun setData(response: Resource<VideoAPIResponse?>?) {
                mLiveData.value = response
            }

        })
    }

    interface DataCallback {
        fun setData(response: Resource<VideoAPIResponse?>?)
    }

}
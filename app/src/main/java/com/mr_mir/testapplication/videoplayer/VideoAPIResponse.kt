package com.mr_mir.testapplication.videoplayer

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Shitab Mir on 22,July,2020
 */
data class VideoAPIResponse (

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: VideoData? = null

): Serializable

data class VideoData (

    @field:SerializedName("video_link")
    val link: String? = null,

    @field:SerializedName("video_thumb")
    val thumb: String? = null
)
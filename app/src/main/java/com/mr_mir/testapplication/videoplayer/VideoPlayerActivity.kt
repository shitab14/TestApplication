package com.mr_mir.testapplication.videoplayer

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mr_mir.testapplication.R
import com.mr_mir.testapplication.UtilSingleton
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.top_actionbar.*

class VideoPlayerActivity : AppCompatActivity(), View.OnClickListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener {

    var context: Context = this
    var viewModel: VideoPlayerViewModel? = null
    var path: String = "videoplayer"

    var mediaPlayer: MediaPlayer? = null

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)

        viewModel = ViewModelProvider(this).get(VideoPlayerViewModel::class.java)

        if (UtilSingleton.isNetworkConnected(context)) {
            loadVideo()
        } else {
            noInternetView()
        }

        tvTitle.text = "Video Application"

        ivBack.setOnClickListener(this)
        ivPlayPause.setOnClickListener(this)
        ivForward.setOnClickListener(this)
        ivRewind.setOnClickListener(this)
        vvVideo.setOnPreparedListener(this)
        vvVideo.setOnCompletionListener(this)
        vvVideo.setOnErrorListener(this)
        vvVideo.setOnInfoListener(this)
        vvVideo.setOnClickListener(this)
//        seeker.setProgress( vvVideo.currentPosition, true)
       // vvVideo.setMediaController(this)
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                loader.visibility = GONE
                return true
            }
            MediaPlayer.MEDIA_INFO_BUFFERING_START -> {
                loaderView()
                return true
            }
            MediaPlayer.MEDIA_INFO_BUFFERING_END -> {
                loader.visibility = GONE
                return true
            }
        }
        return false
    }

    override fun onPrepared(mp: MediaPlayer?) {
        loader.visibility = GONE
    }

    override fun onCompletion(mp: MediaPlayer?) {
        vvVideo.seekTo(0)
        vvVideo.pause()
        playView()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        UtilSingleton.showToast(context, "Something went wrong")
        return true
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.ivPlayPause -> {
                playPauseFunction()
            }
            R.id.ivForward -> {
                if(vvVideo.currentPosition + 11000 < vvVideo.duration) {
                    vvVideo.seekTo(vvVideo.currentPosition + 10000)
                } else {
                    vvVideo.seekTo(0)
                    vvVideo.pause()
                    playView()
                }
            }
            R.id.ivRewind -> {
                if(vvVideo.currentPosition - 11000 > 0) {
                    vvVideo.seekTo(vvVideo.currentPosition - 10000)
                } else {
                    vvVideo.seekTo(0)
                    vvVideo.pause()
                    playView()
                }
            }
            R.id.vvVideo -> {
                playPauseFunction()
            }
        }
    }

    private fun playPauseFunction() {
        if(vvVideo.isPlaying) {
            vvVideo.pause()
            playView()
        } else {
            vvVideo.start()
            pauseView()
        }
    }

    //API Call & Observer
    private fun loadVideo() {
        //Observer to Fetch Data
        viewModel?.mLiveData
            ?.observe(this, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        successView(it.data)
                    }
                    Resource.Status.ERROR -> {
                        failView()
                    }
                    Resource.Status.LOADING -> {
                        loaderView()
                    }
                }
                //viewModel?.removeSourceForData(path)

            })

        //Call to Fetch Data
        viewModel?.fetchData(path)
    }

    //Views
    private fun successView(apiResponse: VideoAPIResponse?) {
        noInternet.visibility = GONE
        val uri: Uri = Uri.parse(apiResponse?.data?.link.toString())
        vvVideo.setVideoURI(uri)
    }

    private fun failView() {
        noInternet.visibility = GONE
        loader.visibility = VISIBLE
        UtilSingleton.showToast(context, "PROBLEM")
    }

    private fun loaderView() {
        noInternet.visibility = GONE
        loader.visibility = VISIBLE
    }

    private fun noInternetView() {
        noInternet.visibility = VISIBLE
        loader.visibility = GONE
    }

    private fun playView() {
        ivPlay.visibility = VISIBLE
        ivPause.visibility = GONE
    }

    private fun pauseView() {
        ivPlay.visibility = GONE
        ivPause.visibility = VISIBLE
    }

}
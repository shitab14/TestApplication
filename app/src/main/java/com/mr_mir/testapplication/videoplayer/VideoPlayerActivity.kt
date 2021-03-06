package com.mr_mir.testapplication.videoplayer

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.*
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mr_mir.testapplication.R
import com.mr_mir.testapplication.UtilSingleton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_video_player.*
import kotlinx.android.synthetic.main.top_actionbar.*


class VideoPlayerActivity : AppCompatActivity(), OnClickListener,
    MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
    MediaPlayer.OnInfoListener {

    private var context: Context = this
    private var viewModel: VideoPlayerViewModel? = null
    private var path: String = "videoplayer"

    private var videoIsLoaded: Boolean = false
    private var savedApiResponse: VideoAPIResponse? = null
    private var handler: Handler = Handler()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_player)
    }

    override fun onResume() {
        super.onResume()

        viewModel = ViewModelProvider(this).get(VideoPlayerViewModel::class.java)

        if (UtilSingleton.isNetworkConnected(context)) {
            loadVideo()
        } else {
            noInternetView()
        }

        tvTitle.text = "Video Application"
        tvDuration.visibility = GONE
        tvNext.visibility = INVISIBLE

        ivBack.setOnClickListener(this)
        ivPlayPause.setOnClickListener(this)
        ivForward.setOnClickListener(this)
        ivRewind.setOnClickListener(this)
        ivRewind10.setOnClickListener(this)
        vvVideo.setOnPreparedListener(this)
        vvVideo.setOnCompletionListener(this)
        vvVideo.setOnErrorListener(this)
        vvVideo.setOnInfoListener(this)
        llVideo.setOnClickListener(this)
//        seeker.setProgress( vvVideo.currentPosition, true)
        // vvVideo.setMediaController(this)
    }

    override fun onInfo(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        when (what) {
            MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START -> {
                loader.visibility = GONE
                ivThumb.visibility = GONE
                updateProgressBar()
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

    @SuppressLint("SetTextI18n")
    override fun onPrepared(mp: MediaPlayer?) {
        videoIsLoaded = true
        loader.visibility = GONE
        tvDuration.text = getFormatTime(vvVideo.duration)
        ivThumb.visibility = VISIBLE
        tvDuration.visibility = VISIBLE
    }

    override fun onCompletion(mp: MediaPlayer?) {
        vvVideo.seekTo(1)
        vvVideo.pause()
        playView()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        /*if(savedApiResponse != null) {
            successView(savedApiResponse)
        } else {
        }*/
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
                ivThumb.visibility = GONE
                if(vvVideo.currentPosition + 11000 < vvVideo.duration) {
                    vvVideo.seekTo(vvVideo.currentPosition + 10000)
                } else {
                    vvVideo.seekTo(1)
                    vvVideo.pause()
                    playView()
                }
            }
            R.id.ivRewind10 -> {
                ivThumb.visibility = GONE
                if(vvVideo.currentPosition - 11000 > 0) {
                    vvVideo.seekTo(vvVideo.currentPosition - 10000)
                } else {
                    vvVideo.seekTo(1)
                }
            }
            R.id.ivRewind -> {
                ivThumb.visibility = GONE
                vvVideo.seekTo(1)
                vvVideo.pause()
                playView()
            }
            R.id.llVideo -> {
                playPauseFunction()
            }
        }
    }

    private fun playPauseFunction() {
        if(videoIsLoaded) {
        ivThumb.visibility = GONE
        if (vvVideo.isPlaying) {
                vvVideo.pause()
                playView()
            } else {
                vvVideo.start()
                pauseView()
            }
        }
    }

    private fun updateProgressBar() {
        handler.postDelayed(updateTimeTask, 100)
    }

    private val updateTimeTask: Runnable = object : Runnable {
        override fun run() {
//            tvCurrentTime.text = getFormatTime(vvVideo.currentPosition)
            seeker.progress = vvVideo.currentPosition
            seeker.max = vvVideo.duration
            handler.postDelayed(this, 100)
        }
    }

    /*fun onProgressChanged(
        seekbar: SeekBar?,
        progress: Int,
        fromTouch: Boolean
    ) {
    }

    fun onStartTrackingTouch(seekbar: SeekBar?) {
        handler.removeCallbacks(updateTimeTask)
    }

    fun onStopTrackingTouch(seekbar: SeekBar?) {
        handler.removeCallbacks(updateTimeTask)
        vvVideo.seekTo(seeker.progress)
        updateProgressBar()
    }*/

    //API Call & Observer
    private fun loadVideo() {
        //Observer to Fetch Data
        viewModel?.mLiveData
            ?.observe(this, Observer {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        savedApiResponse = it.data
                        successView(it.data)
                    }
                    Resource.Status.ERROR -> {
                        failView()
                    }
                    Resource.Status.LOADING -> {
                        loaderView()
                    }
                }

            })

        //Call to Fetch Data
        viewModel?.fetchData(path)
    }

    //Views
    private fun successView(apiResponse: VideoAPIResponse?) {
        noInternet.visibility = GONE
        val uri: Uri = Uri.parse(apiResponse?.data?.link.toString())
        vvVideo.setVideoURI(uri)

        try {
            Picasso.with(context)
                .load(apiResponse?.data?.thumb.toString())
                .placeholder(android.R.color.black)
                .error(R.drawable.ic_baseline_pause_circle_filled_24)
                .fit()
                .noFade()
                .into(ivThumb)
        } catch (e: Exception) {
        }
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

    private fun getFormatTime(milliSeconds: Int): String {
        return "${milliSeconds/60000}:${
        if((milliSeconds%60).toString().length == 1) {
            "0"+(milliSeconds%60).toString()
        } else {
            (milliSeconds%60).toString()
        }
        }"
    }

}
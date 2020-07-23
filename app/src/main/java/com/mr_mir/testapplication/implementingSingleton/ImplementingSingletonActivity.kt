package com.mr_mir.testapplication.implementingSingleton

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mr_mir.testapplication.R
import com.mr_mir.testapplication.UtilSingleton
import com.mr_mir.testapplication.videoplayer.VideoPlayerActivity
import kotlinx.android.synthetic.main.activity_implementing_singleton.*
import kotlinx.android.synthetic.main.top_actionbar.*

class ImplementingSingletonActivity : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implementing_singleton)

        tvTitle.text = "IMPLEMENTING SINGLETON"
        tvNext.setOnClickListener(this)
        ivBack.setOnClickListener(this)

        //For Plane
        SingletonVehicle.wheels = 12
        SingletonVehicle.passengers = 200
        SingletonVehicle.hasGas = true

        tvWheels.text = "The plane has ${SingletonVehicle.wheels} wheels"
        tvPassengers.text = "The plane has ${SingletonVehicle.passengers} passengers"
        if(SingletonVehicle.hasGas) {
            tvGas.text = "Has Gas."
        } else {
            tvGas.text = "No Gas"
        }

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvNext -> {
                UtilSingleton.goToNextActivity(
                    this, VideoPlayerActivity::class.java)
            }
        }
    }
}
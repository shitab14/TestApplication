package com.mr_mir.testapplication.implementinginterface

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mr_mir.testapplication.R
import com.mr_mir.testapplication.UtilSingleton
import com.mr_mir.testapplication.implementingSingleton.ImplementingSingletonActivity
import kotlinx.android.synthetic.main.activity_implementing_interface.*
import kotlinx.android.synthetic.main.top_actionbar.*

class ImplementingInterfaceActivity : AppCompatActivity(), Vehicle, View.OnClickListener {

    private var wheels : Int = 0
    private var passengers : Int = 0
    private var hasGas : Boolean = false


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_implementing_interface)

        tvTitle.text = "IMPLEMENTING INTERFACE"
        tvNext.setOnClickListener(this)
        ivBack.setOnClickListener(this)

        // A Car has
        wheels = 4
        passengers = 5
        hasGas = true

        tvWheels.text = "The Car has ${numberOfWheels()} wheels"
        tvPassengers.text = "The Car has ${numberOfPassengers()} passengers"
        if (hasGas()) {
            tvGas.text = "Has Gas"
            tvGas.setTextColor(Color.GREEN)
        } else {
            tvGas.text = "No Gas"
            tvGas.setTextColor(Color.RED)
        }


    }

    override fun numberOfWheels(): Int {
        return wheels
    }

    override fun numberOfPassengers(): Int {
        return passengers
    }

    override fun hasGas(): Boolean {
        return hasGas
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBack -> {
                finish()
            }
            R.id.tvNext -> {
                UtilSingleton.goToNextActivity(
                    this, ImplementingSingletonActivity::class.java)
            }
        }
    }
}
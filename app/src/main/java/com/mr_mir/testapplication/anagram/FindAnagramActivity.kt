package com.mr_mir.testapplication.anagram

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mr_mir.testapplication.R
import com.mr_mir.testapplication.UtilSingleton
import com.mr_mir.testapplication.implementinginterface.ImplementingInterfaceActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.top_actionbar.*

class FindAnagramActivity : AppCompatActivity(), View.OnClickListener {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTitle.text = "FINDING ANAGRAM"
        tvFindAnagram.setOnClickListener(this)
        tvNext.setOnClickListener(this)
        ivBack.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tvFindAnagram -> {
                if(findAnagram(etA.text.toString(), etB.text.toString())) {
                    tvResults.text = "Yes an Anagram"
                    tvResults.setTextColor(Color.GREEN)
                } else {
                    tvResults.text = "Not an Anagram"
                    tvResults.setTextColor(Color.RED)
                }
            }
            R.id.tvNext -> {
                UtilSingleton.goToNextActivity(
                    this, ImplementingInterfaceActivity::class.java
                )
            }
        }
    }

    private fun findAnagram(a: String, b: String): Boolean {
        val lengthA: Int = a.length
        if (lengthA != b.length || a.isEmpty() || b.isEmpty()) {
            return false
        }
        var temp: Char? = null
        var checker: Boolean
        val arrayB: CharArray = b.toCharArray()
        val lengthArrayB: Int = arrayB.size
        checker = true
        for (i in 0 until lengthA) {
            temp = a[i]
            if (!checker) {
                return false
            }
            checker = false
            for (j in 0 until lengthArrayB) {
                if (temp == arrayB[j]) {
                    arrayB[j] = ' '
                    checker = true
                    break
                } else checker = false
            }
        }
        return checker
    }


}
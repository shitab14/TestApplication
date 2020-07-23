package com.mr_mir.testapplication.anagram

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule

/**
 * Created by Shitab Mir on 23,July,2020
 */
class FindAnagramActivityTest {

    private var context: Context? = null

//    private var input1: ArrayList<String>? = null
//    private var input2: ArrayList<String>? = null
    private var input1: String? = null
    private var input2: String? = null

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(FindAnagramActivity::class.java)
    private var findAnagramActivity: FindAnagramActivity? = null


    @Before
    fun setUp() {
        findAnagramActivity = activityTestRule.activity
        context = InstrumentationRegistry.getInstrumentation().context

        //Input
        input1 = "anagram"
        input2 = "angaram"
    }

    @After
    fun tearDown() {
        findAnagramActivity = null
        input1 = ""
        input2 = ""
    }

    @Test
    fun findAnagram() {
        assertEquals(true, findAnagramActivity?.findAnagram(input1!!, input2!!))
    }
}
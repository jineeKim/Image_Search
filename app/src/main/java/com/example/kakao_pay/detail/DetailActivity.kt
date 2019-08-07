package com.example.kakao_pay.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_detail.*
import android.graphics.Paint
import android.support.v4.view.ViewPager
import android.view.View


class DetailActivity : AppCompatActivity() {

    private lateinit var viewpagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.kakao_pay.R.layout.activity_detail)

        setTransparency()
        setViewpager()
        setOnclick()

    }

    private fun setTransparency() {
        val paint = Paint()
        paint.alpha = 150
        act_detail_rl_exit.setBackgroundColor(paint.color)
    }

    private fun setViewpager() {
        var position = intent.getIntExtra("position", 0)

        viewpagerAdapter = ViewPagerAdapter(this)
        act_detail_vp.adapter = viewpagerAdapter
        act_detail_vp.currentItem = position

        checkFirstPosition()

        act_detail_vp.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                checkFirstPosition()
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {

            }

        })
    }

    private fun setOnclick() {
        act_detail_iv_exit.setOnClickListener {
            finish()
        }

        act_detail_iv_left.setOnClickListener {
            act_detail_vp.setCurrentItem(getItem(-1), true)
        }

        act_detail_iv_right.setOnClickListener {
            act_detail_vp.setCurrentItem(getItem(+1), true)
        }
    }

    private fun getItem(i: Int): Int {
        return act_detail_vp.currentItem + i
    }

    private fun checkFirstPosition() {
        if (act_detail_vp.currentItem == 0) {
            act_detail_iv_left.isClickable = false
            act_detail_iv_left.visibility = View.INVISIBLE
        } else if(act_detail_vp.adapter!!.count == act_detail_vp.currentItem + 1){
            act_detail_iv_right.isClickable = false
            act_detail_iv_right.visibility = View.INVISIBLE
        }
        else {
            act_detail_iv_left.isClickable = true
            act_detail_iv_left.visibility = View.VISIBLE
            act_detail_iv_right.isClickable = true
            act_detail_iv_right.visibility = View.VISIBLE
        }
    }
}

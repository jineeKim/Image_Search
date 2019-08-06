package com.example.kakao_pay.detail

import android.annotation.SuppressLint
import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.kakao_pay.R
import com.example.kakao_pay.main.MainActivity
import com.github.chrisbanes.photoview.PhotoView
import org.jetbrains.anko.startActivity


class ViewPagerAdapter(private val context : Context) : PagerAdapter() {
    private var layoutInflater : LayoutInflater? = null
    val data = MainActivity.documentData.documentDataList


    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view ===  `object`
    }

    override fun getCount(): Int {
       return data.size
    }

    @SuppressLint("InflateParams")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater!!.inflate(R.layout.activity_detail_viewpager , null)
        val photoView = view.findViewById<View>(R.id.act_detail_pv) as PhotoView


        Glide.with(context)
            .load(data[position].image_url)
            .into(photoView)

        photoView.setOnClickListener {
            context.startActivity<WebViewActivity>("url" to data[position].doc_url)
        }

        container.addView(view , 0)

        return view
    }


    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
      val vp = container as ViewPager
        val v = `object` as View
        vp.removeView(v)
    }
}
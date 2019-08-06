package com.example.kakao_pay.main

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import com.bumptech.glide.Glide
import com.example.kakao_pay.data.DocumentData
import org.jetbrains.anko.toast
import android.support.v7.widget.GridLayoutManager
import com.example.kakao_pay.R
import com.example.kakao_pay.detail.DetailActivity
import org.jetbrains.anko.startActivity


class RecyclerViewAdapter(val ctx: Context, val dataList: ArrayList<DocumentData>) :
    RecyclerView.Adapter<RecyclerViewAdapter.Holder>() {
    private var height: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view: View = LayoutInflater.from(ctx).inflate(R.layout.rv_main_document, parent, false)

        height = parent.measuredHeight / 3

        return Holder(view)
    }

    override fun getItemCount(): Int = dataList.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val layoutParams = holder.itemView.layoutParams as GridLayoutManager.LayoutParams
        layoutParams.height = height

        holder.itemView.requestLayout()

        Glide.with(ctx)
            .load(dataList[position].thumbnail_url)
            .into(holder.img)

        holder.item.setOnClickListener {
            ctx.startActivity<DetailActivity>("position" to position)
        }

    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val img: ImageView = itemView.findViewById(R.id.rv_main_img) as ImageView
        val item: RelativeLayout = itemView.findViewById(R.id.rv_main_view)
    }
}
package com.example.kakao_pay.main

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.GridLayoutManager
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.kakao_pay.NetworkService
import com.example.kakao_pay.R
import com.example.kakao_pay.data.DocumentData
import com.example.kakao_pay.data.SearchImageResponse
import com.example.kakao_pay.main.MainActivity.documentData.documentDataList
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.themedTabHost
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private val api: NetworkService = NetworkService.create()

    object documentData {
        val documentDataList = ArrayList<DocumentData>()
    }

    private lateinit var searchWord: String
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        searchData()
        changeSort()

    }

    private fun checkInternet(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo

        return activeNetwork?.isConnectedOrConnecting == true
    }

    private fun init() {
        setSupportActionBar(act_main_toolbar)
        supportActionBar!!.title = ""

        if (!checkInternet()) {
            longToast(getString(R.string.check_network))
        }
    }

    private fun searchData() {
        act_main_iv_search.setOnClickListener {
            searchWord = act_main_et_search_word.text.toString()
            loadData(getString(R.string.accuracy))
            it.hideKeyboard()
        }

        act_main_et_search_word.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                searchWord = act_main_et_search_word.text.toString()
                loadData(getString(R.string.accuracy))
                v.hideKeyboard()
                return@OnKeyListener true
            }
            false
        })


    }

    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }


    private fun setRecyclerView() {
        recyclerViewAdapter = RecyclerViewAdapter(this, documentDataList)

        act_main_rv_search_list.adapter = recyclerViewAdapter
        act_main_rv_search_list.setHasFixedSize(true)
        act_main_rv_search_list.layoutManager = GridLayoutManager(this, 2)
    }


    private fun loadData(sort: String) {
        val getSearchImageResponse = api.getSearchImage(getApiKey(), searchWord, sort)
        getSearchImageResponse.enqueue(object : Callback<SearchImageResponse> {
            override fun onFailure(call: Call<SearchImageResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<SearchImageResponse>, response: Response<SearchImageResponse>) {
                if (response.isSuccessful) {
                    changeTextColor(sort)
                    documentDataList.clear()
                    documentDataList += response.body()!!.documents
                    setRecyclerView()
                }
            }
        })
    }


    private fun getApiKey(): String {
        lateinit var data: String
        val inputStream = resources.openRawResource(R.raw.api_key)
        val byteArrayOutputStream = ByteArrayOutputStream()

        var i: Int
        try {
            i = inputStream.read()
            while (i != -1) {
                byteArrayOutputStream.write(i)
                i = inputStream.read()
            }

            data = String(byteArrayOutputStream.toByteArray())
            inputStream.close()

        } catch (e: IOException) {
            e.printStackTrace()
        }

        return data

    }

    @SuppressLint("ResourceAsColor")
    private fun changeSort() {
        act_main_tv_accuracy.setOnClickListener {
            changeTextColor(getString(R.string.accuracy))
            loadData(getString(R.string.accuracy))
        }

        act_main_tv_recency.setOnClickListener {
            changeTextColor(getString(R.string.recency))
            loadData(getString(R.string.recency))
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeTextColor(sort: String) {
        if (sort == getString(R.string.accuracy)) {
            act_main_tv_accuracy.setTextColor(ContextCompat.getColor(this, R.color.yellow))
            act_main_tv_recency.setTextColor(ContextCompat.getColor(this, R.color.black))
        } else if (sort == getString(R.string.recency)) {
            act_main_tv_recency.setTextColor(ContextCompat.getColor(this, R.color.yellow))
            act_main_tv_accuracy.setTextColor(ContextCompat.getColor(this, R.color.black))

        }

    }
}

package com.aapolis.newsappb35

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.URLUtil
import android.widget.BaseAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.aapolis.newsappb35.adapters.NewsAdapter
import com.aapolis.newsappb35.data.News
import com.aapolis.newsappb35.data.SearchNewsResponse
import com.aapolis.newsappb35.databinding.ActivityMainBinding
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.net.URLEncoder

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: NewsAdapter
    lateinit var queue: RequestQueue
    lateinit var list: List<News>
    val requestTag = "SearchNewsRequestTag"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        queue = Volley.newRequestQueue(baseContext)
        binding.rvNews.layoutManager = LinearLayoutManager(baseContext)

        binding.btnSearch.setOnClickListener {
            searchNews()
        }

        binding.btnCancel.setOnClickListener {
            queue.cancelAll(requestTag)
            binding.processingViews.visibility = View.GONE
            Toast.makeText(baseContext, "Request is cancelled", Toast.LENGTH_LONG).show()
        }
    }

    private fun searchNews() {

        val pd = ProgressDialog(this)
        pd.setTitle("Please wait...!!!")
        pd.setMessage("Please wait. Searching for news.")
        pd.setCancelable(false)


        val searchText = URLEncoder.encode( binding.etSearchText.text.toString(), "UTF-8")


        val url = "${Constants.BASE_URL}search?keywords=$searchText"

        Log.d("SearchUrl", "searchNews: $url")

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                pd.dismiss()
                val gson = Gson()
                val searchResponse: SearchNewsResponse = gson.fromJson(it, SearchNewsResponse::class.java)
                if(searchResponse.status == "ok") {
                    adapter = NewsAdapter(searchResponse.news)
                    binding.rvNews.adapter = adapter
                } else {
                    Toast.makeText(baseContext, "Something went wrong. Please retry.", Toast.LENGTH_LONG).show()
                }
            },
            {
                pd.dismiss()
                //binding.processingViews.visibility = View.GONE
                it.printStackTrace()
                Toast.makeText(baseContext, "Error is : $it", Toast.LENGTH_LONG).show()
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.put("Authorization", Constants.API_KEY)
                map.put("cache-control", "no-cache")
                return map
            }
        }

        val retryPolicy = DefaultRetryPolicy(10 * 1000, 3, 1.5f)
        request.retryPolicy = retryPolicy

        request.tag = requestTag
        queue.add(request)
        //binding.processingViews.visibility = View.VISIBLE
        pd.show()
    }
}
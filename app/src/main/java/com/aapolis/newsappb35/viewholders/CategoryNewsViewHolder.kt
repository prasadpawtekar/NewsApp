package com.aapolis.newsappb35.viewholders

import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aapolis.newsappb35.Constants
import com.aapolis.newsappb35.adapters.NewsAdapter
import com.aapolis.newsappb35.data.SearchNewsResponse
import com.aapolis.newsappb35.databinding.ViewHolderCategoryNewsBinding
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import java.net.URLEncoder
import java.util.*
import kotlin.collections.HashMap

class CategoryNewsViewHolder(val binding: ViewHolderCategoryNewsBinding) :
RecyclerView.ViewHolder(binding.root) {
    val queue = Volley.newRequestQueue(binding.root.context)
    var inputData: String = ""

    fun bind(category: String) {
        val encodedCategory = URLEncoder.encode(category)

        val url = "${Constants.BASE_URL}search?category=$encodedCategory"

        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val newsResponse = gson.fromJson(it, SearchNewsResponse::class.java)
                if(newsResponse.status == "ok") {
                    val adapter = NewsAdapter(newsResponse.news)
                    binding.rvNews.layoutManager = LinearLayoutManager(binding.root.context)
                    binding.rvNews.adapter = adapter
                } else {
                    binding.tvMessage.visibility = View.VISIBLE
                    binding.tvMessage.text = "Failed to load news from $category category"
                }
            },
            {
                it.printStackTrace()
                binding.tvMessage.text = "Failid to load news. Error is : $it "
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.put("Authorization", Constants.API_KEY)
                return map
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun getBody(): ByteArray {

                return Base64.getEncoder().encode(inputData.toByteArray())

            }


        }

        queue.add(request)
    }
}
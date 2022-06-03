package com.aapolis.newsappb35

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.aapolis.newsappb35.adapters.NewsViewPagerAdapter
import com.aapolis.newsappb35.data.CategoryResponse
import com.aapolis.newsappb35.databinding.ActivityCategoryWiseNewsBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson

class CategoryWiseNewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryWiseNewsBinding
    lateinit var queue: RequestQueue
    lateinit var adapter: NewsViewPagerAdapter
    lateinit var list: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryWiseNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        queue = Volley.newRequestQueue(baseContext)
        loadCategories()

    }

    /*private fun loadCategories() {
        list = ArrayList<String>().apply {
            add("regional")
            add("technology")
            add("lifestyle")
            add("business")
            add("general")
            add("programming")
            add("science")
            add("entertainment")
            add("world")
            add("sports")
            add("finance")
            add("academia")
            add("politics")
            add("health")
            add("opinion")
            add("food")
            add("game")
        }

        adapter = NewsViewPagerAdapter(list)
        binding.viewPager.adapter = adapter
        val mediator = TabLayoutMediator(binding.tabs, binding.viewPager) {
                tab, position ->
            tab.text = list[position]
        }
        mediator.attach()
    }*/

    private fun loadCategories() {
        val url = "${Constants.BASE_URL}available/categories"
        val request = object: StringRequest(
            Request.Method.GET,
            url,
            {
                val gson = Gson()
                val response = gson.fromJson(it, CategoryResponse::class.java)
                if(response.status == "ok") {

                    list = response.categories
                    adapter = NewsViewPagerAdapter(list)
                    binding.viewPager.adapter = adapter

                    val mediator = TabLayoutMediator(binding.tabs, binding.viewPager) {
                        tab, position ->
                        tab.text = list[position]
                    }
                    mediator.attach()
                } else {
                    Toast.makeText(baseContext, "Unknown error. Please retry.", Toast.LENGTH_LONG).show()
                }
            },
            {
                it.printStackTrace()
                Toast.makeText(baseContext, "Error is : $it", Toast.LENGTH_LONG).show()
            }
        ){
            override fun getHeaders(): MutableMap<String, String> {
                val map = HashMap<String, String>()
                map.put("Authorization", Constants.API_KEY)
                return map
            }
        }

        queue.add(request)
    }
}
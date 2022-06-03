package com.aapolis.newsappb35.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.aapolis.newsappb35.databinding.ViewHolderCategoryNewsBinding
import com.aapolis.newsappb35.viewholders.CategoryNewsViewHolder

class NewsViewPagerAdapter(val categories: List<String>):
        RecyclerView.Adapter<CategoryNewsViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryNewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderCategoryNewsBinding.inflate(layoutInflater, parent, false)

        return CategoryNewsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CategoryNewsViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

}
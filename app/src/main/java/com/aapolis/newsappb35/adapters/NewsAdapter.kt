package com.aapolis.newsappb35.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aapolis.newsappb35.data.News
import com.aapolis.newsappb35.databinding.ViewHolderNewsBinding
import com.aapolis.newsappb35.viewholders.NewsViewHolder

class NewsAdapter(val list: List<News>): RecyclerView.Adapter<NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewHolderNewsBinding.inflate(layoutInflater, parent, false)
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.setData(list[position])
    }

    override fun getItemCount() = list.size
}
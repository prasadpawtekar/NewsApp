package com.aapolis.newsappb35.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.aapolis.newsappb35.R
import com.aapolis.newsappb35.data.News
import com.aapolis.newsappb35.databinding.ViewHolderNewsBinding
import com.bumptech.glide.Glide

class NewsViewHolder(val binding: ViewHolderNewsBinding): RecyclerView.ViewHolder(binding.root) {

    fun setData(news: News) {
        binding.tvNewsTitle.text = news.title
        binding.tvNewsDesc.text = news.description

        Glide.with(binding.root).load(news.image).placeholder(R.drawable.ic_default_image).into(binding.ivNewsPhoto)


    }
}
package com.example.newsappyt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappyt.databinding.LayoutItemArticlePreviewBinding
import com.example.newsappyt.model.Article

class NewsAdapter : androidx.recyclerview.widget.ListAdapter<Article,NewsAdapter.ViewHolder>
    (
    object : DiffUtil.ItemCallback<Article>()
    {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.toString() == newItem.toString()
        }
    })
{

    inner class ViewHolder(binding:LayoutItemArticlePreviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val  view = LayoutItemArticlePreviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val newsArticle = getItem(position)
        LayoutItemArticlePreviewBinding.bind(holder.itemView).apply()
        {


            textViewArticlePreviewTitle.setText(newsArticle.title)
            textViewArticlePreviewPublishedAt.setText(newsArticle.publishedAt)
            textViewArticlePreviewSource.setText(newsArticle.source.name)
            Glide.with(holder.itemView).load(newsArticle.urlToImage).into(imageViewArticlePreview)


        } // binding close

        holder.itemView.setOnClickListener()
        {

                onItemClickListener?.let { it(newsArticle) }
        }

    } // onBindViewHolder closed


    private var onItemClickListener : ((Article) -> Unit)? = null


    fun setOnCLickListener(listener: (Article)->Unit)
    {
        onItemClickListener = listener
    }



} // NewsAdapter closed
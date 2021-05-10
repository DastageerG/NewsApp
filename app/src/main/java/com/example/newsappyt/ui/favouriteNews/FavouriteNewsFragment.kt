package com.example.newsappyt.ui.favouriteNews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.newsappyt.databinding.FragmentFavouriteNewsBinding
import com.example.newsappyt.model.Article
import com.example.newsappyt.ui.adapter.NewsAdapter
import com.example.newsappyt.ui.breakingNews.BreakingNewsFragmentDirections
import com.example.newsappyt.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class FavouriteNewsFragment : Fragment()
{

    val TAG = "1234"
    private var binding: FragmentFavouriteNewsBinding? = null
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsAdapter: NewsAdapter? = null
    private var articleList:List<Article>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentFavouriteNewsBinding.inflate(inflater,container,false)
        newsAdapter = NewsAdapter()

        setupRecyclerView(binding?.recyclerViewFavouriteNewsFrag)

        newsViewModel.getAllFavouriteArticles()?.observe({lifecycle})
        {list->

            list?.let()
            {
                articleList = it
                newsAdapter?.submitList(articleList)
            } // list closed
        } // observer closed


        newsAdapter?.setOnCLickListener ()
        {

            val action = FavouriteNewsFragmentDirections.actionFragmentFavouriteNewsToArticleFragment(it)
            findNavController().navigate(action)
        }



        return binding?.root
    } // onCreate view closed



    private fun setupRecyclerView(recyclerView: RecyclerView?)
    {

        recyclerView?.apply()
        {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        } // apply closed
        addSwipeToDelete(recyclerView)
    } // setupRecyclerView closed

    private fun addSwipeToDelete(recyclerView: RecyclerView?)
    {
        val itemTouchHelper:ItemTouchHelper.SimpleCallback = object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT)
        {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean
            {
                return false
            } // onMove closed

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int)
            {
                restoreArticle(viewHolder.itemView,articleList?.get(viewHolder.adapterPosition),viewHolder.adapterPosition)

                newsViewModel.deleteArticle(articleList?.get(viewHolder.adapterPosition))
                newsAdapter?.notifyItemRemoved(viewHolder.adapterPosition)
            } // onSwiped closed
        } // itemTouchHelper closed

        val swipeLeft = ItemTouchHelper(itemTouchHelper)
        swipeLeft.attachToRecyclerView(recyclerView)

    } // addSwipeToDelete

    private fun restoreArticle(itemView: View, article: Article?, adapterPosition: Int)
    {
        val snackbar = Snackbar.make(itemView,"Article Deleted",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo")
        {
            newsViewModel.insertToDatabase(article)
            newsAdapter?.notifyItemChanged(adapterPosition)
        }
        snackbar.show()
    }


    override fun onDestroyView()
    {
        super.onDestroyView()
    }
}
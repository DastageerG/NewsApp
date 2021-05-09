package com.example.newsappyt.ui.breakingNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappyt.R
import com.example.newsappyt.databinding.FragmentBreakingNewsBinding
import com.example.newsappyt.ui.adapter.NewsAdapter
import com.example.newsappyt.utils.Resource
import com.example.newsappyt.viewmodel.NewsViewModel
import kotlin.math.log

class BreakingNewsFragment : Fragment()
{
    val TAG = "1234"
    var binding:FragmentBreakingNewsBinding? = null
    val newsViewModel:NewsViewModel by viewModels()
    private var newsAdapter:NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentBreakingNewsBinding.inflate(inflater,container,false)

        setupRecyclerView(binding?.recyclerViewBreakingNews)

        newsViewModel.breakingNews.observe({lifecycle})
        {response ->
            when(response)
            {
                is Resource.Loading -> showProgressBar()
                is Resource.Error ->
                {
                    hideProgressBar()
                    Log.d(TAG, "onCreateView: "+response.data)
                }// Resource Error block closed
                    is Resource.Success->
                    {
                        hideProgressBar()
                        response.data?.let()
                        {
                            newsAdapter?.submitList(it.articles)
                        } // response.data?.let ( null check) closed
                    } // Resource Success block closed
            } // when closed
        } // breaking new observer closed

        return binding?.root
    }

    private fun hideProgressBar()
    {
        binding?.progressBar?.visibility = View.GONE
    }


    private fun showProgressBar()
    {
        binding?.progressBar?.visibility = View.VISIBLE
    }



    private fun setupRecyclerView(recyclerView: RecyclerView?)
    {
        newsAdapter = NewsAdapter()
        recyclerView?.apply()
        {

            adapter = newsAdapter
            layoutManager = LinearLayoutManager(context)
        } // apply closed

    } // setupRecyclerView closed

    override fun onDestroyView()
    {

        binding == null
        super.onDestroyView()
    }
}
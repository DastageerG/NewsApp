package com.example.newsappyt.ui.searchNews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappyt.R
import com.example.newsappyt.databinding.FragmentBreakingNewsBinding
import com.example.newsappyt.databinding.FragmentSearchNewsBinding
import com.example.newsappyt.ui.adapter.NewsAdapter
import com.example.newsappyt.ui.favouriteNews.FavouriteNewsFragmentDirections
import com.example.newsappyt.utils.Constants
import com.example.newsappyt.utils.Resource
import com.example.newsappyt.viewmodel.NewsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment : Fragment()
{

    val TAG = "1234"
    private var binding: FragmentSearchNewsBinding? = null
    private val newsViewModel: NewsViewModel by viewModels()
    private var newsAdapter: NewsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentSearchNewsBinding.inflate(inflater,container,false)

        var job:Job? = null

        binding?.editTextSearchFragmentSearch?.addTextChangedListener()
        {editable ->
            job?.cancel()
            job = MainScope().launch()
            {
                delay(Constants.SEARCH_TIME_DELAY)
                editable?.let()
                {
                    if(editable.toString().isNotEmpty())
                    {
                        newsViewModel.getSearchedNews(editable.toString())
                    } // if closed
                } // editable null check closed

            } // job closed
        } // addTextChanged closed

        setupRecyclerView(binding?.recyclerViewSearchFragment)
        newsViewModel.searchNews.observe({lifecycle})
        {response ->
            when(response)
            {
                is Resource.Loading -> showProgressBar()
                is Resource.Error ->
                {
                    hideProgressBar()
                    Log.d(TAG, "onCreateView: "+response.message)
                    Toast.makeText(context,response.message, Toast.LENGTH_LONG).show()
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
        } // search new observer closed

        newsAdapter?.setOnCLickListener ()
        {

            val action = SearchNewsFragmentDirections.actionFragmentSearchNewsToArticleFragment(it)
            findNavController().navigate(action)
        }



      return  binding?.root
    } // onCreateView closed


    private fun hideProgressBar()
    {
        binding?.progressBarSearchFragment?.visibility = View.GONE
    }


    private fun showProgressBar()
    {
        binding?.progressBarSearchFragment?.visibility = View.VISIBLE
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
        super.onDestroyView()
        binding == null
    }

}
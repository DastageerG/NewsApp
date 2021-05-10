package com.example.newsappyt.ui.articalFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappyt.R
import com.example.newsappyt.databinding.FragmentArticleBinding
import com.example.newsappyt.model.Article
import com.example.newsappyt.ui.adapter.NewsAdapter
import com.example.newsappyt.viewmodel.NewsViewModel
import com.google.android.material.snackbar.Snackbar

class ArticleFragment : Fragment()
{
    private val TAG = "1234"
    private var binding:FragmentArticleBinding? = null
    private val args by navArgs<ArticleFragmentArgs>()
    private val viewModel:NewsViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        binding = FragmentArticleBinding.inflate(inflater,container,false)

        binding?.webViewArticleFrag?.apply()
        {
            webViewClient = WebViewClient()
            loadUrl(args.article.url)
        }


        binding?.floatingActionButtonArticleFrag?.setOnClickListener(View.OnClickListener
        {
            viewModel.insertToDatabase(args.article)
            Snackbar.make(it,"Article Saved",Snackbar.LENGTH_SHORT).show()
        }) // onClickListener closed

        return binding?.root
    } // onCreateView closed



    override fun onDestroyView()
    {
        super.onDestroyView()
        binding == null
    } // onDestroyView closed

} // class closed
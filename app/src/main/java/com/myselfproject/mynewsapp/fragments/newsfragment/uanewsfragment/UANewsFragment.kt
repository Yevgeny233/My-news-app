package com.myselfproject.mynewsapp.fragments.newsfragment.uanewsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myselfproject.mynewsapp.adapters.NewsArticleAdapter
import com.myselfproject.mynewsapp.clicklistener.Clicker
import com.myselfproject.mynewsapp.clicklistener.OnItemClickListener
import com.myselfproject.mynewsapp.databinding.FragmentUANewsBinding
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModel
import com.myselfproject.mynewsapp.models.NewsArticle
import com.myselfproject.mynewsapp.network.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UANewsFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentUANewsBinding
    private lateinit var newsArticleAdapter: NewsArticleAdapter
    private lateinit var networkConnection: NetworkConnection
    private val uaViewModel: UAViewModel by viewModels()
    private val dataViewModel: DataBaseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentUANewsBinding.inflate(inflater, container, false)

        newsArticleAdapter =
            NewsArticleAdapter(
                this
            )

        networkConnection = NetworkConnection(this@UANewsFragment.requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsRecView.adapter = newsArticleAdapter

        uaViewModel.uaNewsItem.observe(viewLifecycleOwner) { newsItem ->
            newsArticleAdapter.submitList(newsItem.articles)
        }

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                uaViewModel.getUANews()
            }
        }

        uaViewModel.statusLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.linearProgressIndicator.visibility = View.VISIBLE
                false -> binding.linearProgressIndicator.visibility = View.GONE
            }
        }
    }

    override fun onSaveButtonClicker(newsArticle: NewsArticle) {
        Clicker().saveClicker(newsArticle, dataViewModel, this)
    }

    override fun onShareButtonClicker(newsArticle: NewsArticle) {
        Clicker().shareClicker(newsArticle, this)
    }

    override fun onItemClick(newsArticle: NewsArticle) {
        Clicker().itemClicker(newsArticle, this, networkConnection)
    }
}
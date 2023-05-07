package com.myselfproject.mynewsapp.fragments.newsfragment.bbcnewsfragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myselfproject.mynewsapp.adapters.NewsArticleAdapter
import com.myselfproject.mynewsapp.databinding.FragmentBBCBinding
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModel
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModelFactory
import com.myselfproject.mynewsapp.models.NewsArticle
import com.myselfproject.mynewsapp.network.NetworkConnection
import com.myselfproject.mynewsapp.network.NetworkService
import com.myselfproject.mynewsapp.usecases.*

class BBCNewsFragment : Fragment(), OnItemClickListener, OnSaveButtonClicker, OnShareButtonClicker {
    private lateinit var binding: FragmentBBCBinding
    private lateinit var newsArticleAdapter: NewsArticleAdapter
    private lateinit var bbcViewModel: BBCViewModel
    private val networkService = NetworkService.getInstance()
    private lateinit var networkConnection: NetworkConnection
    private lateinit var dataViewModel: DataBaseViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentBBCBinding.inflate(inflater, container, false)

        dataViewModel = ViewModelProvider(
            this, DataBaseViewModelFactory(this.requireContext())
        )[DataBaseViewModel::class.java]

        bbcViewModel = ViewModelProvider(
            this, BBCViewModelFactory(NewsRepository(networkService))
        )[BBCViewModel::class.java]

        newsArticleAdapter =
            NewsArticleAdapter(
                this,
                this,
                this
            )

        networkConnection = NetworkConnection(this@BBCNewsFragment.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsRecView.adapter = newsArticleAdapter

        bbcViewModel.bbcNewsItem.observe(viewLifecycleOwner) { newsItem ->
            newsArticleAdapter.submitList(newsItem.articles)

        }

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                bbcViewModel.getBBCNews()
            }
        }

        bbcViewModel.statusLoading.observe(viewLifecycleOwner) {
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
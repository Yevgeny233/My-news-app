package com.myselfproject.mynewsapp.fragments.newsfragment.wsjnewsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myselfproject.mynewsapp.adapters.NewsArticleAdapter
import com.myselfproject.mynewsapp.databinding.FragmentWSJNewsBinding
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModel
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModelFactory
import com.myselfproject.mynewsapp.models.NewsArticle
import com.myselfproject.mynewsapp.network.NetworkConnection
import com.myselfproject.mynewsapp.network.NetworkService
import com.myselfproject.mynewsapp.usecases.Clicker
import com.myselfproject.mynewsapp.usecases.NewsRepository
import com.myselfproject.mynewsapp.usecases.OnItemClickListener


class WSJNewsFragment : Fragment(), OnItemClickListener {

    private lateinit var binding: FragmentWSJNewsBinding
    private lateinit var wsjViewModel: WSJViewModel
    private val networkService = NetworkService.getInstance()
    private lateinit var adapter: NewsArticleAdapter
    private lateinit var networkConnection: NetworkConnection
    private lateinit var dataViewModel: DataBaseViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        binding = FragmentWSJNewsBinding.inflate(inflater, container, false)

        dataViewModel = ViewModelProvider(
            this, DataBaseViewModelFactory(this.requireContext())
        )[DataBaseViewModel::class.java]

        wsjViewModel = ViewModelProvider(
            this, WSJViewModelFactory(NewsRepository(networkService))
        )[WSJViewModel::class.java]

        adapter = NewsArticleAdapter(
            this
        )

        networkConnection = NetworkConnection(this@WSJNewsFragment.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.newsRecView.adapter = adapter

        wsjViewModel.newsWSJItem.observe(viewLifecycleOwner) { newsItem ->
            adapter.submitList(newsItem?.articles?.filter {
                it.url.startsWith("https://www.wsj.com/articles/")
            })
        }

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                wsjViewModel.getWSJNews()
            }
        }

        wsjViewModel.statusLoading.observe(viewLifecycleOwner) {
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

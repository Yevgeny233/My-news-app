package com.myselfproject.mynewsapp.fragments.selectfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myselfproject.mynewsapp.adapters.SelectAdapter
import com.myselfproject.mynewsapp.clicklistener.Clicker
import com.myselfproject.mynewsapp.clicklistener.OnDataArticleClicker
import com.myselfproject.mynewsapp.databinding.FragmentSelectBinding
import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.network.NetworkConnection
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectFragment : Fragment(), OnDataArticleClicker {
    private lateinit var binding: FragmentSelectBinding
    private lateinit var dataAdapter: SelectAdapter
    private val dataViewModel: DataBaseViewModel by viewModels()
    private lateinit var networkConnection: NetworkConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSelectBinding.inflate(inflater, container, false)

        dataAdapter = SelectAdapter(this)

        networkConnection = NetworkConnection(this@SelectFragment.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.adapter = dataAdapter

        dataViewModel.dataBaseNews.observe(viewLifecycleOwner) { newsList ->
            if (newsList.isNotEmpty()) {
                binding.dataBaseIsClear.visibility = View.GONE
                dataAdapter.updateList(newsList)
            } else {
                binding.dataBaseIsClear.visibility = View.VISIBLE
                dataAdapter.updateList(newsList)
            }
        }
    }

    override fun deleteButtonClick(dataArticle: DataArticle) {
        dataViewModel.deleteArticle(dataArticle)

    }

    override fun shareButtonClick(dataArticle: DataArticle) {
        Clicker().shareClicker(dataArticle, this)
    }

    override fun onArticleClicker(dataArticle: DataArticle) {
        Clicker().itemClicker(dataArticle, this, networkConnection)
    }
}
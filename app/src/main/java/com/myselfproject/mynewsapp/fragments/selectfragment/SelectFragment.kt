package com.myselfproject.mynewsapp.fragments.selectfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.myselfproject.mynewsapp.adapters.SelectAdapter
import com.myselfproject.mynewsapp.databinding.FragmentSelectBinding
import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.network.NetworkConnection
import com.myselfproject.mynewsapp.usecases.Clicker
import com.myselfproject.mynewsapp.usecases.OnArticleClicker
import com.myselfproject.mynewsapp.usecases.OnButtonClicker


class SelectFragment : Fragment(), OnButtonClicker, OnArticleClicker {
    private lateinit var binding: FragmentSelectBinding
    private lateinit var dataAdapter: SelectAdapter
    private lateinit var dataViewModel: DataBaseViewModel
    private lateinit var networkConnection: NetworkConnection

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSelectBinding.inflate(inflater, container, false)

        dataViewModel = ViewModelProvider(
            this,
            DataBaseViewModelFactory(this.requireContext())
        )[DataBaseViewModel::class.java]

        dataAdapter = SelectAdapter(this, this)

        networkConnection = NetworkConnection(this@SelectFragment.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recycleView.adapter = dataAdapter

            dataViewModel.dataBaseNews.observe(viewLifecycleOwner) { newsList ->
                if (newsList.isNotEmpty()){
                    binding.dataBaseIsClear.visibility = View.GONE
                    dataAdapter.updateList(newsList)
                }else{
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
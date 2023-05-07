package com.myselfproject.mynewsapp.usecases

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.myselfproject.mynewsapp.R
import com.myselfproject.mynewsapp.fragments.selectfragment.DataBaseViewModel
import com.myselfproject.mynewsapp.models.DataArticle
import com.myselfproject.mynewsapp.models.NewsArticle
import com.myselfproject.mynewsapp.network.NetworkConnection

class Clicker {
    fun saveClicker(
        newsArticle: NewsArticle, dataViewModel: DataBaseViewModel, fragment: Fragment
    ) {
        val newDataArticle = DataArticle(
            newsArticle.source?.name,
            newsArticle.author,
            newsArticle.title,
            newsArticle.description,
            newsArticle.url,
            newsArticle.urlToImage,
            newsArticle.publishedAt,
            newsArticle.content
        )
        var isContain = false

        dataViewModel.dataBaseNews.observe(fragment.viewLifecycleOwner) { listDataArticle ->
            listDataArticle.forEach { dataArticle ->
                if (dataArticle.url.equals(newDataArticle.url)) {
                    isContain = true
                }

            }
        }
        when (isContain) {
            false -> dataViewModel.addArticle(newDataArticle)
            true -> Toast.makeText(
                fragment.requireContext(),
                "News about ${newsArticle.title} saved already",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    fun shareClicker(newsArticle: NewsArticle, fragment: Fragment) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, newsArticle.urlToImage)
            type = "image/jpeg"
            putExtra(Intent.EXTRA_TEXT, newsArticle.title)
            putExtra(Intent.EXTRA_TEXT, newsArticle.url)
            type = "text/plain"

        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        fragment.startActivity(shareIntent)
    }

    fun shareClicker(dataArticle: DataArticle, fragment: Fragment) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, dataArticle.urlToImage)
            type = "image/jpeg"
            putExtra(Intent.EXTRA_TEXT, dataArticle.title)
            putExtra(Intent.EXTRA_TEXT, dataArticle.url)
            type = "text/plain"

        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        fragment.startActivity(shareIntent)
    }

    fun itemClicker(
        newsArticle: NewsArticle, fragment: Fragment, networkConnection: NetworkConnection
    ) {
        networkConnection.observe(fragment.viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val bundle = Bundle()
                bundle.putString("keyUrl", newsArticle.url)
                fragment.findNavController().navigate(R.id.webFragment, bundle)
            } else {
                Toast.makeText(
                    fragment.requireContext(),
                    "Sorry don't have connection with internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    fun itemClicker(
        dataArticle: DataArticle, fragment: Fragment, networkConnection: NetworkConnection
    ) {
        networkConnection.observe(fragment.viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                val bundle = Bundle()
                bundle.putString("keyUrl", dataArticle.url)
                fragment.findNavController().navigate(R.id.webFragment, bundle)
            } else {
                Toast.makeText(
                    fragment.requireContext(),
                    "Sorry don't have connection with internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}
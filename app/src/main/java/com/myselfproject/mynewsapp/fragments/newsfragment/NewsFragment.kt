package com.myselfproject.mynewsapp.fragments.newsfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.myselfproject.mynewsapp.R
import com.myselfproject.mynewsapp.adapters.ViewPagerAdapter
import com.myselfproject.mynewsapp.databinding.FragmentNewsBinding
import com.myselfproject.mynewsapp.network.NetworkConnection


class NewsFragment : Fragment() {
    private lateinit var binding: FragmentNewsBinding
    private lateinit var networkConnection: NetworkConnection
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNewsBinding.inflate(inflater, container, false)

        networkConnection = NetworkConnection(this@NewsFragment.requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        networkConnection.observe(viewLifecycleOwner) { isConnected ->
            if (isConnected) {
                Toast.makeText(this@NewsFragment.context, getString(R.string.internet_is_connected), Toast.LENGTH_SHORT)
                    .show()
                binding.imageDisconnect.visibility = View.GONE

            } else {
                Toast.makeText(
                    this@NewsFragment.context, getString(R.string.internet_is_not_connected), Toast.LENGTH_SHORT
                ).show()
            }
        }
        binding.viewPager2.adapter = ViewPagerAdapter(this@NewsFragment)

        val tabLayoutMediator = setTabLayout()

        tabLayoutMediator.attach()

    }

    private fun setTabLayout() =
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getText(R.string.wall_street_journal_news)
                }
                1 -> {
                    tab.text = getText(R.string.bbc_news)
                }
                2 -> {
                    tab.text = getText(R.string.ua_news)
                }
            }
        }
}


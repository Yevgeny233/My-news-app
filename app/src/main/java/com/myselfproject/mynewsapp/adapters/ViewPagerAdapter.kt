package com.myselfproject.mynewsapp.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.myselfproject.mynewsapp.fragments.newsfragment.bbcnewsfragment.BBCNewsFragment
import com.myselfproject.mynewsapp.fragments.newsfragment.uanewsfragment.UANewsFragment
import com.myselfproject.mynewsapp.fragments.newsfragment.wsjnewsfragment.WSJNewsFragment


class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WSJNewsFragment()
            1 -> BBCNewsFragment()
            2 -> UANewsFragment()
            else -> {
                throw RuntimeException("Invalid position")
            }
        }
    }

}


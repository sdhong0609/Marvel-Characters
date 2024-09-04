package com.hongstudio.marvelcharacters.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hongstudio.marvelcharacters.ui.favorite.FavoriteFragment
import com.hongstudio.marvelcharacters.ui.search.SearchFragment

class ViewPagerAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {
    private val fragments: List<Fragment> = listOf(SearchFragment(), FavoriteFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}

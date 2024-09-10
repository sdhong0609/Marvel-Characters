package com.hongstudio.marvelcharacters.ui

import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseActivity
import com.hongstudio.marvelcharacters.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(
    inflater = ActivityMainBinding::inflate
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setUpView()
    }

    private fun setUpView() {
        binding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.page_search_title)
                1 -> getString(R.string.page_favorite_title)
                else -> ""
            }
        }.attach()
    }
}

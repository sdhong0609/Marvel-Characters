package com.hongstudio.marvelcharacters.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseFragment
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.databinding.FragmentFavoriteBinding
import com.hongstudio.marvelcharacters.ui.CharacterListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter = CharacterListAdapter(::onClickItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewFavorites?.adapter = adapter

        viewModel.favoriteCharacters.observe {
            adapter.submitList(it)

            binding?.textViewEmptyFavorite?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun onClickItem(item: LocalCharacter) {
        viewModel.deleteFavorite(item)
    }
}

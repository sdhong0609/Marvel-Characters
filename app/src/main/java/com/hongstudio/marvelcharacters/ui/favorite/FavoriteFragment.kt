package com.hongstudio.marvelcharacters.ui.favorite

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseFragment
import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import com.hongstudio.marvelcharacters.databinding.FragmentFavoriteBinding
import com.hongstudio.marvelcharacters.ui.CharacterListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(
    layoutId = R.layout.fragment_favorite,
    binder = FragmentFavoriteBinding::bind
) {
    private val viewModel: FavoriteViewModel by viewModels()
    private val adapter = CharacterListAdapter(::onItemClick)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewFavorites?.adapter = adapter

        viewModel.favoriteCharacters.observe {
            adapter.submitList(it)

            binding?.textViewEmptyFavorite?.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
        }

        viewModel.isLoadingVisible.observe {
            binding?.progressBar?.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.error.observe {
            val context = this.context
            if (context != null) {
                Toast.makeText(
                    context,
                    it.message ?: context.getString(R.string.common_error_message),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onItemClick(item: CharacterLocal) {
        viewModel.deleteFavorite(item)
    }
}

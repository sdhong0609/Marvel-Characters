package com.hongstudio.marvelcharacters.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseFragment
import com.hongstudio.marvelcharacters.data.source.network.Character
import com.hongstudio.marvelcharacters.databinding.FragmentSearchBinding
import com.hongstudio.marvelcharacters.ui.CharacterListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(
    layoutId = R.layout.fragment_search,
    binder = FragmentSearchBinding::bind
) {
    private val viewModel: SearchViewModel by viewModels()
    private val adapter = CharacterListAdapter(::onClickItem)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.recyclerViewSearched?.adapter = adapter

        binding?.editTextSearch?.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.onKeywordChanged(text.toString())
        })

        viewModel.searchedCharacters.observe {
            adapter.submitList(it)
        }
    }

    private fun onClickItem(item: Character) {
        viewModel.onClickItem(item)
    }
}

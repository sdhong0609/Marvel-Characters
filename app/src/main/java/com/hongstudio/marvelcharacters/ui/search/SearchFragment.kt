package com.hongstudio.marvelcharacters.ui.search

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseFragment
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
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
        binding?.recyclerViewSearched?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as GridLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter!!.itemCount - 1

                if (!recyclerView.canScrollVertically(1) && lastVisibleItemPosition == itemTotalCount) {
                    viewModel.loadMoreCharacters()
                }
            }
        })

        binding?.editTextSearch?.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.onKeywordChanged(text.toString())
        })

        viewModel.searchedCharacters.observe {
            adapter.submitList(it)
        }

        viewModel.isLoadingVisible.observe {
            binding?.progressBar?.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    private fun onClickItem(item: LocalCharacter) {
        viewModel.onClickItem(item)
    }
}

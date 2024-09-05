package com.hongstudio.marvelcharacters.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.marvelcharacters.base.BaseListAdapter
import com.hongstudio.marvelcharacters.base.BaseViewHolder
import com.hongstudio.marvelcharacters.data.source.network.Character

class CharacterListAdapter(
    private val onClickItem: (item: Character) -> Unit,
) : BaseListAdapter<Character>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, Character> {
        return ItemCharacterViewHolder(
            parent = parent,
            onClickItem = onClickItem
        ) as BaseViewHolder<ViewBinding, Character>
    }
}

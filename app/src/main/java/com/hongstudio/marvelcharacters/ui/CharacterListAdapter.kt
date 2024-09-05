package com.hongstudio.marvelcharacters.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.marvelcharacters.base.BaseListAdapter
import com.hongstudio.marvelcharacters.base.BaseViewHolder
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter

class CharacterListAdapter(
    private val onClickItem: (item: LocalCharacter) -> Unit,
) : BaseListAdapter<LocalCharacter>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, LocalCharacter> {
        return ItemCharacterViewHolder(
            parent = parent,
            onClickItem = onClickItem
        ) as BaseViewHolder<ViewBinding, LocalCharacter>
    }
}

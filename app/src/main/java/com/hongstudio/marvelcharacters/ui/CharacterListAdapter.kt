package com.hongstudio.marvelcharacters.ui

import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.hongstudio.marvelcharacters.base.BaseListAdapter
import com.hongstudio.marvelcharacters.base.BaseViewHolder
import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal

class CharacterListAdapter(
    private val onItemClick: (item: CharacterLocal) -> Unit,
) : BaseListAdapter<CharacterLocal>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding, CharacterLocal> {
        return CharacterViewHolder(
            parent = parent,
            onItemClick = onItemClick
        ) as BaseViewHolder<ViewBinding, CharacterLocal>
    }
}

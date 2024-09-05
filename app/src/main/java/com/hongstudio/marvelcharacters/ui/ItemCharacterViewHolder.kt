package com.hongstudio.marvelcharacters.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.marvelcharacters.base.BaseViewHolder
import com.hongstudio.marvelcharacters.data.source.network.Character
import com.hongstudio.marvelcharacters.databinding.ItemCharacterBinding

class ItemCharacterViewHolder(
    parent: ViewGroup,
    private val onClickItem: (item: Character) -> Unit
) : BaseViewHolder<ItemCharacterBinding, Character>(
    ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(item: Character) {
        binding.imageViewCharacter.load("${item.thumbnail.path}.${item.thumbnail.extension}")
        binding.textViewName.text = item.name
        binding.textViewDescription.text = item.description

        binding.root.setOnClickListener {
            onClickItem(item)
        }
    }
}

package com.hongstudio.marvelcharacters.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.hongstudio.marvelcharacters.R
import com.hongstudio.marvelcharacters.base.BaseViewHolder
import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.databinding.ItemCharacterBinding

class ItemCharacterViewHolder(
    private val parent: ViewGroup,
    private val onClickItem: (item: LocalCharacter) -> Unit
) : BaseViewHolder<ItemCharacterBinding, LocalCharacter>(
    ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    override fun bind(item: LocalCharacter) {
        binding.imageViewCharacter.load(item.thumbnailUrl)
        binding.textViewName.text = item.name
        binding.textViewDescription.text = item.description

        binding.root.setCardBackgroundColor(
            parent.context.resources.getColor(
                if (item.isFavorite) R.color.gray else R.color.white,
                null
            )
        )

        binding.root.setOnClickListener {
            onClickItem(item)
        }
    }
}

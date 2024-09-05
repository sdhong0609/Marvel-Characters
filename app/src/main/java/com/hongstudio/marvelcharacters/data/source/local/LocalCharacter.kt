package com.hongstudio.marvelcharacters.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hongstudio.marvelcharacters.base.BaseViewHolderItem

@Entity(tableName = "favorites")
data class LocalCharacter(
    @PrimaryKey
    override val id: Int,
    val name: String,
    val description: String,
    val thumbnailUrl: String,
    val isFavorite: Boolean = false,
    val timestamp: Long = 0L
) : BaseViewHolderItem

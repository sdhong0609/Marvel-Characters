package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.LocalCharacter
import com.hongstudio.marvelcharacters.data.source.network.Character

fun Character.toLocal() = LocalCharacter(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "${thumbnail.path}.${thumbnail.extension}"
)

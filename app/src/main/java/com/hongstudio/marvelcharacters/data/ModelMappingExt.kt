package com.hongstudio.marvelcharacters.data

import com.hongstudio.marvelcharacters.data.source.local.CharacterLocal
import com.hongstudio.marvelcharacters.data.source.network.Character

fun Character.toLocal() = CharacterLocal(
    id = id,
    name = name,
    description = description,
    thumbnailUrl = "${thumbnail.path}.${thumbnail.extension}"
)

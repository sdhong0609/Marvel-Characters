package com.hongstudio.marvelcharacters.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("results")
    val results: List<Character>
)

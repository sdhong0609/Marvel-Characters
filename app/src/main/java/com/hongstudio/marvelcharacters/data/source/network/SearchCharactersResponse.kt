package com.hongstudio.marvelcharacters.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchCharactersResponse(
    @SerialName("data")
    val data: Data
)

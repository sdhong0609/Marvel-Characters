package com.hongstudio.marvelcharacters.data.source.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Character(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("thumbnail")
    val thumbnail: Thumbnail
) {
    @Serializable
    data class Thumbnail(
        @SerialName("path")
        val path: String,
        @SerialName("extension")
        val extension: String
    )
}


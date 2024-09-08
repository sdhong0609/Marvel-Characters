package com.hongstudio.marvelcharacters.utils

import com.hongstudio.marvelcharacters.BuildConfig
import java.security.MessageDigest

@OptIn(ExperimentalStdlibApi::class)
fun getHash(ts: String): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest((ts + BuildConfig.API_PRIVATE_KEY + BuildConfig.API_PUBLIC_KEY).toByteArray())
    return digest.toHexString()
}

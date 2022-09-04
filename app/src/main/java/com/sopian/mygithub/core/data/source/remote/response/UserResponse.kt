package com.sopian.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val name: String? = null,
    val bio: String? = null,
)

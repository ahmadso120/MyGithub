package com.sopian.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchUsersResponse(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val id: Int,
    val login: String,
    var query: String?
)
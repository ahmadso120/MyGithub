package com.sopian.mygithub.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserRepositoriesResponse(
    val id: Int,
    val name: String,
    val description: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int? = null
)

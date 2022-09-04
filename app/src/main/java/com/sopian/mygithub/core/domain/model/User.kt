package com.sopian.mygithub.core.domain.model

data class User(
    val login: String,
    val id: Int,
    val avatarUrl: String,
    val name: String? = null,
    val bio: String? = null,
)

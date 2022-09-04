package com.sopian.mygithub.core.domain.model

data class UserRepositories(
    val id: Int,
    val name: String,
    val description: String? = null,
    val updatedAt: String,
    val stargazersCount: Int? = null
)
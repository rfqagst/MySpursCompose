package com.example.submissioncompose.model

data class Player(
    val id: Long,
    val name: String,
    val desc: String,
    val position : String,
    val nationality : String,
    val photoId: Int,
    var isFavorite: Boolean = false,
)
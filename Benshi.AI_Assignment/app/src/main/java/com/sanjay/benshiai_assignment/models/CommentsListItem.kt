package com.sanjay.benshiai_assignment.models

data class CommentsListItem(
    val body: String,
    val email: String,
    val id: Int,
    val name: String,
    val postId: Int
)
package com.sanjay.benshiai_assignment.models

data class PostListItem(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    var postDetails : PostDetails
)
package com.sanjay.benshiai_assignment.retrofit

import com.sanjay.benshiai_assignment.models.CommentsList
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.models.PostListItem
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostAPI {

    @GET("/posts")
    suspend fun getPost(@Query("_page") page : Int) : PostList



     @GET("/users")
    suspend fun getPostDetail(@Query("id") id : Int) : ArrayList<PostDetails>

     @GET("/posts/{id}/comments")
     suspend fun getPostComments(@Path("id") id : Int) : CommentsList


}
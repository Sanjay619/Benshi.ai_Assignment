package com.sanjay.benshiai_assignment.retrofit

import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PostAPI {

    @GET("/posts")
    suspend fun getPost(@Query("_page") page : Int) : PostList

    @GET("/users")
    suspend fun getPostDetail(@Query("id") id : Int) : ArrayList<PostDetails>

    @GET("/users")
     fun getPostDetail2(@Query("id") id : Int) : Observable<ArrayList<PostDetails>>


}
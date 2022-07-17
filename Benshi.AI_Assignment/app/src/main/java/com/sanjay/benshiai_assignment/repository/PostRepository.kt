package com.sanjay.benshiai_assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.paging.PostPagingSource
import com.sanjay.benshiai_assignment.retrofit.PostAPI
import io.reactivex.Observable
import javax.inject.Inject

class PostRepository @Inject constructor(private val postApi : PostAPI){

    fun getPost() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { PostPagingSource(postApi) }
    ).liveData

   suspend fun getPostDetail(id : Int) : ArrayList<PostDetails>{

        return postApi.getPostDetail(id)
    }
     fun getPostDetail2(id : Int) : Observable<ArrayList<PostDetails>>{

        return postApi.getPostDetail2(id)
    }

}
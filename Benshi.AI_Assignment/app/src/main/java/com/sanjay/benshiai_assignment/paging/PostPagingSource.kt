package com.sanjay.benshiai_assignment.paging

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.retrofit.PostAPI
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class PostPagingSource(val postApi: PostAPI) : PagingSource<Int, PostListItem>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostListItem> {
        return try {
            val position = params.key ?: 1
            val response = postApi.getPost(position)

            response.forEach {
             CoroutineScope(Dispatchers.Default).launch {
                 Log.d("Data Source", "load: ${it}")
                 getCommentsObservable(it)
                // val detail = postApi.getPostDetail(it.id)
                 //Log.e("Data Source", "load: ${detail}")
             }
             }

            LoadResult.Page(
                data = response,
                prevKey = if(position == 1) null else position -1,
                nextKey = if(position == 10) null else position + 1
            )


           // loadPostDetail(response, params)
        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }
    fun getCommentsObservable(post: PostListItem): Observable<PostListItem>? {
        Log.d(TAG, "getCommentsObservable: => ")


        return postApi.getPostDetail2(post.id)
            .map { details ->
                val delay: Int = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
                Thread.sleep(delay.toLong())
                Log.d(
                    TAG,
                    "apply: sleeping thread " + Thread.currentThread().name + " for " + delay.toString() + "ms"
                )
                if (details != null) {
                    //  post.postDetails = details
                    Log.d(TAG, "apply: $details")
                }

                post
            }.subscribeOn(Schedulers.io())


    }


//    suspend fun getCommentsObservable(post: PostListItem): Observable<PostListItem>? {
//        return postApi.getPostDetail(post.id)
//            .map(object :
//                io.reactivex.functions.Function<PostDetails,  PostListItem> {
//                override fun apply(details: PostDetails): PostListItem? {
//
//                    val delay: Int = (Random().nextInt(5) + 1) * 1000 // sleep thread for x ms
//                    Thread.sleep(delay.toLong())
//                    Log.d(
//                        TAG,
//                        "apply: sleeping thread " + Thread.currentThread().name + " for " + delay.toString() + "ms"
//                    )
//                    if (details != null) {
//                        post.postDetails = details
//                        Log.d("112", "apply: $details")
//                    }
//
//                    return post
//
//                }
//
//
//            }).subscribeOn(Schedulers.io())
//
//
//    }

    override fun getRefreshKey(state: PagingState<Int, PostListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
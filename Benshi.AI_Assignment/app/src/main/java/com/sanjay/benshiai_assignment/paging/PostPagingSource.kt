package com.sanjay.benshiai_assignment.paging

import android.content.ContentValues.TAG
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState

import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.retrofit.PostAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PostPagingSource(val postApi: PostAPI) : PagingSource<Int, PostListItem>(){

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PostListItem> {
        return try {
            val position = params.key ?: 1


           val response = postApi.getPost(position)
              LoadResult.Page(
                data = response,
                prevKey = if(position == 1) null else position -1,
                nextKey = if(position == 10) null else position + 1
            )



        }catch(e: Exception){
            LoadResult.Error(e)
        }
    }





    override fun getRefreshKey(state: PagingState<Int, PostListItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


}
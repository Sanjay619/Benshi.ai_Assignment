package com.sanjay.benshiai_assignment.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.sanjay.benshiai_assignment.models.CommentsList
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.paging.PostPagingSource
import com.sanjay.benshiai_assignment.retrofit.PostAPI
import com.sanjay.benshiai_assignment.viewModels.PostViewModel_HiltModules_KeyModule_ProvideFactory
import io.reactivex.Observable
import javax.inject.Inject

class PostRepository @Inject constructor(private val postApi : PostAPI): BaseRepository() {

    fun getPost() = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 100),
        pagingSourceFactory = { PostPagingSource(postApi) }
    ).liveData



   suspend fun getPostDetail(id : Int) = safeApiCall {

        postApi.getPostDetail(id)
    }

    suspend fun getPostCommnents(id : Int) = safeApiCall{
         postApi.getPostComments(id)
    }


}
package com.sanjay.benshiai_assignment.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository : PostRepository) : ViewModel() {

    val list = postRepository.getPost().cachedIn(viewModelScope)

    suspend fun getPostDetail(id: Int): ArrayList<PostDetails> {
        return postRepository.getPostDetail(id)
    }


     fun getPostDetail2(id: Int): Observable<ArrayList<PostDetails>> {
        return postRepository.getPostDetail2(id)
    }


}
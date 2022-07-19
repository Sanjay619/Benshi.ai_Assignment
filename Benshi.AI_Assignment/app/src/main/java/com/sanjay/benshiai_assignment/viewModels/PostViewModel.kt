package com.sanjay.benshiai_assignment.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(private val postRepository : PostRepository) : ViewModel() {

    val list = postRepository.getPost().cachedIn(viewModelScope)



}
package com.sanjay.benshiai_assignment.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.benshiai_assignment.models.CommentsList
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.repository.PostRepository
import com.sanjay.benshiai_assignment.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class PostDetailViewModel @Inject constructor(private val postRepository : PostRepository) : ViewModel() {


    val postDetail : MutableLiveData<Resource<ArrayList<PostDetails>>> = MutableLiveData()
    val postDetailLiveData : LiveData<Resource<ArrayList<PostDetails>>> get() = postDetail

     fun getPostDetail(id: Int) = viewModelScope.launch{
        postDetail.value = postRepository.getPostDetail(id)

    }

    val comments : MutableLiveData<Resource<CommentsList>> = MutableLiveData()
    val commentLiveData : LiveData<Resource<CommentsList>> get() = comments

    fun getComments(id : Int) = viewModelScope.launch{
        comments.value = postRepository.getPostCommnents(id)
    }


}
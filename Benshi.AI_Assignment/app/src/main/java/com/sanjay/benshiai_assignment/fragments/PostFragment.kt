package com.sanjay.benshiai_assignment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagingData
import androidx.paging.flatMap
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.benshiai_assignment.R
import com.sanjay.benshiai_assignment.models.PostDetails
import com.sanjay.benshiai_assignment.models.PostList
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.paging.PostPagingAdapter
import com.sanjay.benshiai_assignment.viewModels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Post.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class PostFragment : Fragment() {
    private lateinit var rvPost : RecyclerView
    lateinit var padapter : PostPagingAdapter
    lateinit var postViewModel : PostViewModel
    private  val TAG = "PostDetailFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        initUI(view)
        setAdapter()
        getDetail();
        getCommentsObservable()





        return view;


    }

    private fun getDetail() {
        CoroutineScope(Dispatchers.Default).launch {
           val res = postViewModel.getPostDetail(2)
            Log.e(TAG, "getDetail: $res")
        }
    }

    private fun setAdapter() {
        padapter = PostPagingAdapter(this)
        rvPost.layoutManager = LinearLayoutManager(context)
        rvPost.setHasFixedSize(true)
        rvPost.adapter = padapter

        postViewModel.list.observe(viewLifecycleOwner , Observer {
         padapter.submitData(lifecycle, it)
        })


    }





    fun getCommentsObservable(): Observable<PostListItem>? {
        Log.d(TAG, "getCommentsObservable: => ")

        val post = null
        return postViewModel.getPostDetail2(1)
            .map(object :
                io.reactivex.functions.Function< List<PostDetails>,  PostListItem> {
                override fun apply(details: List<PostDetails>): PostListItem? {

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

                    return post

                }


            }).subscribeOn(Schedulers.io())


    }

    private fun initUI(view : View) {
        try {
            rvPost = view.findViewById(R.id.rvPost)
        //    val factory = ViewModelFactory(PostRepository())
            postViewModel = ViewModelProvider(this).get(PostViewModel::class.java)
            Log.d(TAG, "initUI: => ")
        }catch (e : Exception){
            Log.d(TAG, "initUI: => $e")
        }

    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Post.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PostFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
package com.sanjay.benshiai_assignment.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.benshiai_assignment.R
import com.sanjay.benshiai_assignment.adapter.LoaderAdapter
import com.sanjay.benshiai_assignment.interfaces.OnSelectItem
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.adapter.PostPagingAdapter
import com.sanjay.benshiai_assignment.viewModels.PostViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostFragment : Fragment(), OnSelectItem {
    private lateinit var rvPost: RecyclerView
    private lateinit var padapter: PostPagingAdapter
    private lateinit var postViewModel: PostViewModel


    private val TAG = "PostDetailFragment"

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


        return view
    }

    // init Adapter for show Post
    private fun setAdapter() {
        val itemSelection: OnSelectItem = this
        padapter = PostPagingAdapter(itemSelection)
        rvPost.layoutManager = LinearLayoutManager(context)
        rvPost.setHasFixedSize(true)
        rvPost.adapter = padapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(),
            footer = LoaderAdapter()
        )

        postViewModel.list.observe(viewLifecycleOwner, Observer {
            padapter.submitData(lifecycle, it)
        })


    }


    private fun initUI(view: View) {
        try {
            rvPost = view.findViewById(R.id.rvPost)
            postViewModel = ViewModelProvider(this)[PostViewModel::class.java]
        } catch (e: Exception) {

        }

    }
    //post selection event after selection you can go post detail fragment
    override fun onItemSelection(post: PostListItem) {

        Log.e(TAG, "onItemSelection: $post")
        val bundle = Bundle().apply {
            putInt("id", post.id)
            putInt("userId", post.userId)
            putString("title", post.title)
            putString("body", post.body)
        }

        Navigation.findNavController(rvPost).navigate(R.id.postDetailFragment, bundle)
        activity?.setTitle("Post Details")
    }

}
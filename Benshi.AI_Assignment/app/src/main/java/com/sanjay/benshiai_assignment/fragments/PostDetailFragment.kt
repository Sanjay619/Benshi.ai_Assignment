package com.sanjay.benshiai_assignment.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.sanjay.benshiai_assignment.R
import com.sanjay.benshiai_assignment.adapter.CommentsAdapter
import com.sanjay.benshiai_assignment.databinding.FragmentPostDetailBinding
import com.sanjay.benshiai_assignment.models.CommentsList
import com.sanjay.benshiai_assignment.models.PostListItem
import com.sanjay.benshiai_assignment.repository.Resource
import com.sanjay.benshiai_assignment.viewModels.PostDetailViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostDetailFragment : Fragment(), View.OnClickListener, OnMapReadyCallback {


    private lateinit var post: PostListItem
    private val TAG = "PostDetailFragment"
    private lateinit var binding: FragmentPostDetailBinding
    private lateinit var postViewModel: PostDetailViewModel
    private lateinit var googleMap: GoogleMap
    private var location: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            val id = arguments?.getInt("id") as Int
            val userId = arguments?.getInt("userId") as Int
            val title = arguments?.getString("title") as String
            val body = arguments?.getString("body") as String
            post = PostListItem(body, id, title, userId, null)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap(view)
        setData()
        loadImage()
        loadPostDetail()
        setListner()
    }

    //   private lateinit var  mapFragment : SupportMapFragment
    private fun initMap(view: View) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        mapFragment?.getMapAsync(this)
    }

    private fun setListner() {
        binding.tvPhoneNo.setOnClickListener(this)
        binding.tvWebsite.setOnClickListener(this)
        binding.tvComments.setOnClickListener(this)
        binding.tvEmail.setOnClickListener(this)
    }

    // Requesting for Getting  User Detail.. and set In Text fields...!
    @SuppressLint("SetTextI18n")
    private fun loadPostDetail() {

        postViewModel = ViewModelProvider(this).get(PostDetailViewModel::class.java)
        postViewModel.getPostDetail(post.id)
        postViewModel.postDetailLiveData.observe(viewLifecycleOwner, Observer {

            when(it){
                is Resource.Success ->{
                    for (item in it.value) {
                        try {
                            post.postDetails = item
                            Log.e(TAG, "loadPostDetail: $item")
                            binding.tvName.text = "Name : " + item.name
                            binding.tvEmail.text = "Email : " + item.email
                            binding.tvPhoneNo.text = "Phone : " + item.phone
                            binding.tvWebsite.text = "Website : " + item.website
                            val lat = post.postDetails?.address?.geo?.lat
                            val lng = post.postDetails?.address?.geo?.lng
                            location = LatLng(lat!!, lng!!)
                            addMarketOnMap()
                        }catch(e : Exception){

                        }

                    }
                }
                is Resource.Failure ->{
                    Snackbar.make(
                        binding.flComment,
                        "Something went wrong please try again",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }

        })
    }
    // Loading image in ImageView using glide
    private fun loadImage() {
        val imageUrl = "https://picsum.photos/seed/${post.title}/300/300"
        Glide.with(this)
            .load(imageUrl)
            .into(binding.ivCoverImage);
    }

    //Setting Data in field the data is coming from previous fragment
    private fun setData() {
        binding.tvTitle.text = post.title
        binding.tvBody.text = post.body

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

            binding.tvComments.id -> {
                loadPotComments()
            }
            binding.tvWebsite.id -> {
                openWebsite()
            }
            binding.tvEmail.id -> {
                openEmail()
            }
            binding.tvPhoneNo.id -> {
                openPhneBook()
            }

        }
    }
    // Show Phone Dialer
    private fun openPhneBook() {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", post.postDetails?.phone, null))
        startActivity(intent)
    }
    // Open User Email
    private fun openEmail() {
        val intent = Intent(Intent.ACTION_VIEW)
        val data: Uri = Uri.parse(
            "mailto:"
                    + post.postDetails?.email

        )
        intent.data = data
        startActivity(intent)
    }
    // Open Website
    private fun openWebsite() {

        var url = post.postDetails?.website
        if (!url?.startsWith("https://")!! && !url.startsWith("http://")) {
            url = "http://$url";
        }
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }
    // Loading comment of the post
    private fun loadPotComments() {

        binding.pDialog.visibility = View.VISIBLE
        postViewModel.getComments(post.id)

        postViewModel.commentLiveData.observe(viewLifecycleOwner, Observer {
            binding.pDialog.visibility = View.GONE
            when (it) {
                is Resource.Success -> {
                    if (it.value.size > 0) {
                        setAdapter(it.value)
                    } else {
                        Snackbar.make(
                            binding.flComment,
                            "Something went wrong please try again",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
                is Resource.Failure -> {
                    Snackbar.make(binding.flComment, "Something went wrong please try again", Snackbar.LENGTH_SHORT)
                        .show()
                }
            }


        })
    }
    // Setting comments Adapter
    private fun setAdapter(commentsList: CommentsList) {
        //   binding.flComment
        binding.flComment.visibility = View.VISIBLE
        val commentsAdapter = CommentsAdapter(commentsList)

        binding.rvComments.layoutManager = LinearLayoutManager(context)
        binding.rvComments.setHasFixedSize(true)
        binding.rvComments.adapter = commentsAdapter
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
    }
    // Adding marker on google map with help of user detail API response Address
    private fun addMarketOnMap() {
        googleMap.addMarker(
            MarkerOptions()
                .position(location!!)
                .title(
                    post.postDetails?.address?.suite
                            + " " + post.postDetails?.address?.street
                            + " " + post.postDetails?.address?.city
                )
        )
        // Moving map camera on particular Location
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location!!, 10f));
    }




}
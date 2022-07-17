package com.sanjay.benshiai_assignment.paging

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sanjay.benshiai_assignment.R
import com.sanjay.benshiai_assignment.fragments.PostFragment
import com.sanjay.benshiai_assignment.models.PostListItem

class PostPagingAdapter(var testReference : PostFragment) : PagingDataAdapter<PostListItem, PostPagingAdapter.PostViewHolder>(COMPARATOR) {

    private lateinit var context : Context
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val item = getItem(position)
        if(item != null){
            holder.tvTitle.text = item.title
            holder.tvBody.text = item.body
            loadImage(item.title, holder.ivPostImg)
            this.gettingPostDetail(item)
        }

    }

    private fun loadImage(title: String, ivPostImg : ImageView) {
        val ImageUrl = "https://picsum.photos/seed/$title/130/160"
        Glide.with(context)
            .load(ImageUrl)
            .into(ivPostImg);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        context  = parent.context

        return PostViewHolder(view)
    }


    fun gettingPostDetail(item: PostListItem) {
//        testReference.getCommentsObservable(item)
    }




    class PostViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvBody = itemView.findViewById<TextView>(R.id.tvBody)
        val ivPostImg = itemView.findViewById<ImageView>(R.id.ivPostImg)
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PostListItem>() {
            override fun areItemsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PostListItem, newItem: PostListItem): Boolean {
                return oldItem == newItem
            }

        }
    }




}
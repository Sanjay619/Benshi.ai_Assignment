package com.sanjay.benshiai_assignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sanjay.benshiai_assignment.R
import com.sanjay.benshiai_assignment.models.CommentsList
import com.sanjay.benshiai_assignment.models.CommentsListItem

class CommentsAdapter(var commentsList : CommentsList) : RecyclerView.Adapter<CommentsAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.comments_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = commentsList.get(position)
        holder.bindData(item)
    }

    override fun getItemCount(): Int {
        return commentsList.size
    }

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView){

        private val tvName = itemView.findViewById<TextView>(R.id.tvName)
        private val tvComments = itemView.findViewById<TextView>(R.id.tvCommentBody)
        private val tvEmail = itemView.findViewById<TextView>(R.id.tvEmail)
        fun bindData(item: CommentsListItem) {
            tvName.text = item.name
            tvComments.text = item.body
            tvEmail.text = item.email

        }

    }
}
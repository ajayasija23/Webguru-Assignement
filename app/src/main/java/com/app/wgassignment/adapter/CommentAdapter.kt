package com.app.wgassignment.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.wgassignment.activities.DetailActivity
import com.app.wgassignment.databinding.ItemCommentBinding
import com.app.wgassignment.model.CommentItem

class CommentAdapter(val mContext:Context, val list:List<CommentItem>):RecyclerView.Adapter<CommentAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.MyViewHolder {
        return MyViewHolder(
            ItemCommentBinding.inflate(
                LayoutInflater.from(mContext),parent,false
            )
        )
    }

    override fun onBindViewHolder(holder: CommentAdapter.MyViewHolder, position: Int) {
        holder.binding.tvName.text=list[position].name
        holder.binding.tvEmail.text=list[position].email
        holder.binding.tvDesc.text=list[position].body
        holder.binding.root.setOnClickListener {
            val intent=Intent(mContext,DetailActivity::class.java)
            intent.putExtra("item",list[position])
            mContext.startActivity(intent)
        }
    }

    override fun getItemCount()=list.size
    inner class MyViewHolder(val binding:ItemCommentBinding):RecyclerView.ViewHolder(binding.root)
}
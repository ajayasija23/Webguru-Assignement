package com.app.wgassignment.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.app.wgassignment.adapter.CommentAdapter
import com.app.wgassignment.databinding.ActivityCommentsBinding
import com.app.wgassignment.network.Status
import com.app.wgassignment.util.isOnline
import com.app.wgassignment.util.toast
import com.app.wgassignment.viewModels.CommentViewModel


class CommentsActivity:BaseActivity() {
    private lateinit var binding:ActivityCommentsBinding
    private val viewModel:CommentViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCommentsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Comments"
        binding.swipeLayout.setOnRefreshListener {
            callApi()
        }
        callApi()
        bindObserver()
    }

    private fun callApi() {
       if (isOnline(this)){
           viewModel.getComments()
       }else{
           binding.rvComment.visibility=View.GONE
           binding.rlNoInternet.visibility= View.VISIBLE
           binding.swipeLayout.isRefreshing=false
       }
    }

    private fun bindObserver() {
        viewModel.comments.observe(this,{
            binding.swipeLayout.isRefreshing=false
            binding.rlNoInternet.visibility=View.GONE
            binding.rvComment.visibility=View.VISIBLE
            when(it.status){
                Status.LOADING->{
                    showProgress()
                }
                Status.SUCCESS->{
                    hideProgress()
                    binding.rvComment.adapter=CommentAdapter(this,it.data!!)
                }
                Status.ERROR->{
                    hideProgress()
                    toast(it.message.toString())
                }
            }
        })
    }
}
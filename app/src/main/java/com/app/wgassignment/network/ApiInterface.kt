package com.app.wgassignment.network

import com.app.wgassignment.model.*
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {


    @GET("comments")
    suspend fun getComments():Response<List<CommentItem>>



}
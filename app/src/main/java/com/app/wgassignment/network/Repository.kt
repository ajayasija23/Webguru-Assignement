package com.app.wgassignment.network

import com.app.wgassignment.model.CommentItem
import retrofit2.Response

class Repository {
    val baseUrl="https://jsonplaceholder.typicode.com/"
    val api=ApiClient.getClient(baseUrl)?.create(ApiInterface::class.java)

    suspend fun getComments():Response<List<CommentItem>>{
        return api!!.getComments()
    }


}
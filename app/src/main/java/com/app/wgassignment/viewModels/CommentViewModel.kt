package com.app.wgassignment.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.wgassignment.model.*
import com.app.wgassignment.network.Repository
import com.app.wgassignment.network.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CommentViewModel:ViewModel() {
    private val repository=Repository()

    val comments= MutableLiveData<Resource<List<CommentItem>>>()

    fun getComments(){
        comments.value=Resource.loading(null)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = repository.getComments()
                withContext(Dispatchers.Main)
                {
                    Log.d("response","$response")
                    if (response != null) {
                        when(response.isSuccessful){
                            true->{
                                comments.value=Resource.success(response.body(),response.message())
                            }
                            false->{
                                comments.value=Resource.error(null,response.message())
                            }
                        }
                    }
                }
            } catch (t: Throwable) {
                withContext(Dispatchers.Main)
                {
                    comments.value = Resource.error(data = null, message = t.localizedMessage)
                }
            }
        }
    }

}
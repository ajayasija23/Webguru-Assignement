package com.app.wgassignment.activities

import android.os.Bundle
import com.app.wgassignment.databinding.ActivityDetailsBinding
import com.app.wgassignment.model.CommentItem

class DetailActivity:BaseActivity() {
    private lateinit var binding:ActivityDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title="Comment Details"
        setData()
    }

    private fun setData() {
        val item=intent?.getParcelableExtra<CommentItem>("item")
        binding.tvFullName.text=item?.name
        binding.tvEmail.text=item?.email
        //======================= Display Name Containing only vowels in name===============================
        binding.tvDisplayName.text=getDisplayName(item?.name)
    }

    private fun getDisplayName(name: String?): String? {
        var vowels="aeiou"
        var displayName=""

        for (i in 0 until name!!.length){
            val char=name[i]
            if (vowels.contains(char,ignoreCase = true)&&!displayName.lowercase().contains(char,ignoreCase = true)){
                displayName+=char
            }
        }

        return displayName
    }

}
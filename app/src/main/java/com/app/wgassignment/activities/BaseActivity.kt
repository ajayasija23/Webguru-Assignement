package com.app.wgassignment.activities

import android.R
import android.app.AlertDialog
import android.app.Dialog
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.app.wgassignment.databinding.LayoutProgressbarBinding


open class BaseActivity : AppCompatActivity() {
    var dialog: Dialog? = null
    private var binding: LayoutProgressbarBinding? = null

    fun showProgress() {
        dialog = Dialog(this)
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding = LayoutProgressbarBinding.inflate(
            layoutInflater
        )
        val view: View = binding!!.root
        dialog!!.window!!.setBackgroundDrawableResource(R.color.transparent)
        dialog!!.setContentView(view)
        dialog!!.setCancelable(false)
        dialog!!.show()
    }

    fun hideProgress() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
            dialog = null
        }
    }

    private fun dismiss(builder: AlertDialog.Builder) {
        builder.create().dismiss()
    }

}
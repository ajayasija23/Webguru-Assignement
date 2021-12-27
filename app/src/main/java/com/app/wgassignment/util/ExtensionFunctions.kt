package com.app.wgassignment.util

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.InputStream
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun Context.toast(msg:String){
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}
fun EditText.emailValid():Boolean{
    return !TextUtils.isEmpty(text) && android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()
}
fun EditText.passwordValid():Boolean{
    val pattern: Pattern
    val matcher: Matcher

    val PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$"

    pattern = Pattern.compile(PASSWORD_PATTERN)
    matcher = pattern.matcher(this.text.toString())

    return matcher.matches()
}
fun EditText.valid():Boolean{
    return text.toString().isEmpty()
}

fun String.showDialog(context: Context, onClickOk: (() -> Unit)?){
    val alert=AlertDialog.Builder(context)
    alert.setMessage(this)
    alert.setPositiveButton("Ok") { p0, p1 ->
        run {
            alert.create().dismiss()
            if(onClickOk!=null){
                onClickOk()
            }
        }
    }
    alert.show()
}

fun Fragment.toast(msg: String){
    Toast.makeText(this.requireContext(),msg,Toast.LENGTH_SHORT).show()
}

fun Context.getOsVersion():String{
    val fields = Build.VERSION_CODES::class.java.fields
    var codeName = "UNKNOWN"
    fields.filter { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }
        .forEach { codeName = it.name }
    return codeName
}

fun log(msg: String){
    Log.d("cliffting",msg)
}


fun Context.openDatePicker(onResult:(result:String)->Unit){
    val c = Calendar.getInstance()

    val mYear = c[Calendar.YEAR]
    val mMonth = c[Calendar.MONTH]
    val mDay = c[Calendar.DAY_OF_MONTH]

    val datePickerDialog = DatePickerDialog(
        this,
        { _, year, monthOfYear, dayOfMonth ->

            val day: String = if (dayOfMonth.toString().length == 1) {
                "0$dayOfMonth"
            } else {
                dayOfMonth.toString()
            }

            val month = if (monthOfYear<9) {
                "0${monthOfYear + 1}"
            } else {
                "${monthOfYear + 1}"
            }
            onResult("$year-$month-$day")
        }, mYear, mMonth, mDay
    )
    datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 1000;

    datePickerDialog.show()

}

fun Context.getMultipartArray(imageList: ArrayList<File>, key: String): ArrayList<MultipartBody.Part> {
    val list=ArrayList<MultipartBody.Part>()
    for (file in imageList){
        val requestBody=file.asRequestBody("image/*".toMediaTypeOrNull())
        list.add(MultipartBody.Part.createFormData(key,file.name,requestBody))
    }
    return list
}

fun String.encode256():String{
    val bytes = this.toByteArray()
    val md = MessageDigest.getInstance("SHA-256")
    val digest = md.digest(bytes)
    return digest.fold("", { str, it -> str + "%02x".format(it) })
}

fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        var capabilities: NetworkCapabilities?=null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            capabilities=connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        } else {
            return connectivityManager.activeNetworkInfo?.isConnected!!
        }
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

fun Date.format(format:String):String{
    val simpleDateFormat=SimpleDateFormat(format)
    return simpleDateFormat.format(this)
}

fun InputStream.saveToFile(dir: File, file: String) = use { input ->
    File(dir,file).outputStream().use { output ->
        input.copyTo(output)
    }
}

fun Context.createAdapter(placeHolder:String,list:MutableList<String>): ArrayAdapter<String> {
    list.add(0,placeHolder)
    val adapter=ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,list)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    return adapter
}

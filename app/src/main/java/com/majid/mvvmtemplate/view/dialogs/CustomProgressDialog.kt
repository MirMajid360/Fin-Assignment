package com.majid.mvvmtemplate.view.dialogs

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.TextView
import com.majid.mvvmtemplate.R

class CustomProgressDialog(context: Context) : Dialog(context) {
    init {

        try {


        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.layout_progress_dialog)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCancelable(false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setMessage(message: String) {
        try {


        val messageTextView = findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = message

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

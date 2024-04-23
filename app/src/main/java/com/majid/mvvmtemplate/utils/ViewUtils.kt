package com.majid.mvvmtemplate.utils


import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.snackbar.Snackbar
import com.majid.mvvmtemplate.R
import com.majid.mvvmtemplate.view.dialogs.CustomProgressDialog

enum class STATUS_BAR_THEME {
    COLOR_THEME, WHITE_THEME
}

class ViewUtils {

    companion object {
        var progressDialog: CustomProgressDialog? = null
        fun showToast(context: Context, msg: String) {

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

        }


        fun showProgressDialog(context: Context, msg: String) {
            progressDialog = CustomProgressDialog(context)
            progressDialog?.setMessage(msg)
            progressDialog?.show()

        }

        fun hideProgressDialog() {

            progressDialog?.dismiss()
        }

        @SuppressLint("ObsoleteSdkInt")
        fun setupStatusBar(activity: Activity, theme: STATUS_BAR_THEME) {

            try {


                when (theme) {
                    STATUS_BAR_THEME.COLOR_THEME -> {

                        try {


                            //                val statusBarDrawable = ContextCompat.getDrawable(activity, R.drawable.status_bar_top_part)
                            activity.window.statusBarColor = activity.resources.getColor(
                                R.color.gradient_top,
                                null
                            ) // Clear any previous status bar color
                            //                activity.window.setBackgroundDrawable(statusBarDrawable)
                            activity.window.navigationBarColor =
                                activity.resources.getColor(R.color.gradient_bottom, null)
                            val wic = WindowCompat.getInsetsController(
                                activity.window,
                                activity.window.decorView
                            )
                            wic.isAppearanceLightStatusBars = false

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    STATUS_BAR_THEME.WHITE_THEME -> {

                        try {


                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                val window: Window = activity.window ?: return
                                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                                window.statusBarColor =
                                    ContextCompat.getColor(activity, R.color.app_white)
                                activity.window.navigationBarColor =
                                    activity.resources.getColor(R.color.app_white, null)

                                val wic = WindowCompat.getInsetsController(
                                    activity.window,
                                    activity.window.decorView
                                )
                                wic.isAppearanceLightStatusBars = true
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }


                }

            } catch (e: Exception) {
                e.printStackTrace()
            }


        }


    }
}
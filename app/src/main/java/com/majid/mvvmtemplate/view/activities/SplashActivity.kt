package com.majid.mvvmtemplate.view.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import com.majid.mvvmtemplate.R
import com.majid.mvvmtemplate.databinding.ActivitySplashBinding
import com.majid.mvvmtemplate.utils.STATUS_BAR_THEME
import com.majid.mvvmtemplate.utils.ViewUtils
import com.majid.mvvmtemplate.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/** *
 * The below code is written by Majid Manzor Mir for the purpose of Assignment for FinInfoCom on 23/April/2024 at  7:30 am
 * **/
@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)

        setContentView(binding.root)

        ViewUtils.setupStatusBar(this,STATUS_BAR_THEME.WHITE_THEME)

        setupWindow()

        setAppVersion()

        animateLogo()


        moveForward()

    }

    /**
     *  This function is used to show thw App version on splash , for now Im showing the different text
     * **/

    @SuppressLint("SetTextI18n")
    private fun setAppVersion() {
        try {
//            val pInfo = packageManager.getPackageInfo(packageName, 0)
//            val version = pInfo.versionName
//            binding.tvAppVersion.text = "Version $version"
            binding.tvAppVersion.text = "Android Assignmant \n by \n Majid Manzoor Mir"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
    }

    private fun animateLogo() {
        val animation = AnimationUtils.loadAnimation(this, R.anim.animate_splash_logo)
        binding.ivLogo.startAnimation(animation)
    }

    /**
     * The  below function is  used to set the full screen
     * activity with matching colors for status bar and navigation bar
     */
    private fun setupWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        // transparent color ful screen
        // you can set any custom color from colors file
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT
        val wic = WindowCompat.getInsetsController(window, window.decorView)
        wic.isAppearanceLightStatusBars = true
    }

    /**
    This function checks for the login status and move accordingly
     **/
    private fun moveForward() {

        try {


            val handler = Handler(Looper.myLooper()!!)
            handler.postDelayed({
                try {
                    // delayed code here
                    // move forward according to  Login Status

                    mainViewModel.getUserLoggedInStatus().observe(this, Observer {
                        if (it) {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this, AuthActivity::class.java))
                            finish()
                        }
                    })


                } catch (e: java.lang.Exception) {

                }

            }, 3000)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
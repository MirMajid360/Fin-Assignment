package com.majid.mvvmtemplate.view.activities

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.view.WindowCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.majid.mvvmtemplate.R
import com.majid.mvvmtemplate.databinding.ActivityAuthBinding
import com.majid.mvvmtemplate.utils.Constants
import com.majid.mvvmtemplate.utils.STATUS_BAR_THEME
import com.majid.mvvmtemplate.utils.Utils
import com.majid.mvvmtemplate.utils.ViewUtils
import com.majid.mvvmtemplate.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding

    @Inject
   lateinit var mainViewModel: MainViewModel

    // [START declare_auth]
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupWindow()
        auth = Firebase.auth
        ViewUtils.setupStatusBar(this, STATUS_BAR_THEME.WHITE_THEME)
        handleBackPress()
        binding.btnLogin.setOnClickListener {
            if (validated()){
                ViewUtils.showProgressDialog(this,"Logging in...")
                signIn(binding.etUserName.text.toString().trim(),binding.etPassword.text.toString().trim())
            }
        }


    }

    /***
     * the below function is used to handle the device back click
     * **/

    var hasBackButtonClicked = false
    private fun handleBackPress() {

        try {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        // Back is pressed

                        // If on the menu fragment and back stack count is 1 or less
                        if (hasBackButtonClicked) {
                            finish()
                        } else {
                            ViewUtils.showToast(this@AuthActivity, "Press BACK again to exit.")
                            hasBackButtonClicked = true
                            val handler = Looper.myLooper()?.let { Handler(it) }
                            handler?.postDelayed({
                                hasBackButtonClicked = false
                            }, 1000)
                        }

                    }
                }
            )

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    /**
     * The  below function is  used to set the full screen
     * activity with matching colors for status bar and navigation bar
     *  I am using the same function in Splash------Most preferred way is to create a utility function and call the function whenever needed
     */

    private fun setupWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        }
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = getColor(R.color.app_white)
        val wic = WindowCompat.getInsetsController(window, window.decorView)
        wic.isAppearanceLightStatusBars = true
    }


    private fun validated(): Boolean {

        if (binding.etUserName.text.toString().trim().isEmpty()) {
            binding.etUserName.error = "Enter Username"
            return false
        }
        if (binding.etUserName.text.toString().trim().length <10) {
            binding.etUserName.error = "Username must be 10 characters"
            return false
        }

        if (binding.etPassword.text.toString().trim().isEmpty()) {
            binding.etPassword.error = "Enter Password"
            return false
        }
        val validationResultString = Utils.isValidPassword(binding.etPassword.text.toString().trim())

        if(validationResultString == Constants.PASSWORD_VALIDATED){

        }else{
            binding.etPassword.error = validationResultString
        }
        return true
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    ViewUtils.hideProgressDialog()
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    ViewUtils.hideProgressDialog()
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    updateUI(null)
                }
            }
        // [END sign_in_with_email]
    }

    /***
     * The below function is used to Update the UI with user details or
     * If we need to save the user details in local or send to the server
     * For now I am only saving the login status
     * **/
    private fun updateUI(user: FirebaseUser?) {


        user?.let {

            mainViewModel.saveUserLoggedInStatus(true)



            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }


    }
    companion object {
        private const val TAG = "Tagged"
    }


}
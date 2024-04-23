package com.majid.mvvmtemplate.view.activities

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.majid.mvvmtemplate.R
import com.majid.mvvmtemplate.databinding.ActivityMainBinding
import com.majid.mvvmtemplate.models.User
import com.majid.mvvmtemplate.utils.IListeners
import com.majid.mvvmtemplate.utils.STATUS_BAR_THEME
import com.majid.mvvmtemplate.utils.ViewUtils
import com.majid.mvvmtemplate.view.adapters.UserAdapter
import com.majid.mvvmtemplate.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var mainViewModel: MainViewModel

    lateinit var userAdapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewUtils.setupStatusBar(this, STATUS_BAR_THEME.WHITE_THEME)
        setListeners()
        mainViewModel.loadData()
        setObserver()
        initUserAdapter()
        handleBackPress()


    }

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
                                ViewUtils.showToast(this@MainActivity, "Press BACK again to exit.")
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

    private fun showOptionsMenu() {
        val popup = PopupMenu(this, binding.ivFilter, 0, 0, R.style.PopupMenu)
        popup.menuInflater.inflate(R.menu.menu_options, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            // Handle menu item clicks here
            when (item.itemId) {
                R.id.sortByName -> {
                    // Sort By Name
                    Toast.makeText(this@MainActivity, "Sort by Name", Toast.LENGTH_SHORT).show()

                    userAdapter.sortByName()

                    true
                }


                R.id.sortByAge -> {
                    // Sort By Name
                    Toast.makeText(this@MainActivity, "Sort by Age", Toast.LENGTH_SHORT).show()

                    userAdapter.sortByAge()
                    true
                }

                R.id.sortByCity -> {
                    // Sort By Name
                    Toast.makeText(this@MainActivity, "Sort by City", Toast.LENGTH_SHORT).show()

                    userAdapter.sortByCity()
                    true
                }

                else -> false
            }
        }
        popup.show()
    }

    /**
     * The Below function is user to obser thlive data i view model so whenever there will be change in the data inside viewModel
     * out view will get updated
     * **/

    private fun setObserver() {
        mainViewModel.userData.observe(this, Observer { users ->
            // Update UI with users

            userAdapter.updateList(ArrayList(users))
        })
    }

    private fun setListeners() {
        binding.ivFilter.setOnClickListener {
            // show menu
            showOptionsMenu()
        }
    }

    private fun initUserAdapter() {

        try {


            userAdapter = UserAdapter(this, arrayListOf(), object : IListeners.IClickListeners {
                override fun onItemClicked(model: Any, pos: Int) {
                    if (model is User) {
                        Toast.makeText(
                            this@MainActivity,
                            "${model.name} Clicked",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }
            }
            )


            val layoutManager = LinearLayoutManager(this)
            binding.recyclerView.layoutManager = layoutManager
            binding.recyclerView.adapter = userAdapter


        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


}
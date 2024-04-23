package com.majid.mvvmtemplate.app

import android.app.Application
import android.content.Context
import com.majid.mvvmtemplate.utils.Constants.Database_name
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class App : Application() {
    companion object {

        lateinit var context: Context
    }

    override fun onCreate() {
        try {


            super.onCreate()
            context = this

            Realm.init(this)
            val config: RealmConfiguration =
                RealmConfiguration.Builder().name("${Database_name}").schemaVersion(1)
                    .allowQueriesOnUiThread(true).allowWritesOnUiThread(true).build()
            Realm.setDefaultConfiguration(config)

//
//
            //   val backgroundThreadRealm : Realm = Realm.getInstance(config)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
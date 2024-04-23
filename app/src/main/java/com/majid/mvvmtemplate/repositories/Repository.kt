package com.majid.mvvmtemplate.repositories

import com.majid.mvvmtemplate.models.User
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject


class Repository @Inject constructor(private val realm: Realm) {


    fun insertUsers(users: List<User>) {
        realm.executeTransaction { realm ->
            realm.insertOrUpdate(users)
        }
    }

    fun getUsers(): RealmResults<User> {
        return realm.where(User::class.java).findAll()
    }

    fun hasData(): Boolean {
        return realm.where(User::class.java).count() > 0
    }

    fun closeRealm(){
        realm.close()
    }

}
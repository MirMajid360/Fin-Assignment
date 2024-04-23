package com.majid.mvvmtemplate.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import io.realm.annotations.Required

@RealmClass
open class User(
    @PrimaryKey
    var id: Int = 0,
    @Required var name: String = "",
    var age: Int = 0,
    var city: String = ""
) : RealmObject()
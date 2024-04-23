package com.majid.mvvmtemplate.di

import com.majid.mvvmtemplate.repositories.PreferencesRepository
import com.majid.mvvmtemplate.repositories.Repository
import com.majid.mvvmtemplate.viewmodels.MainViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {

    @Provides
    @Singleton
    fun providesViewModel(preferencesRepository: PreferencesRepository, repository: Repository): MainViewModel {
        return MainViewModel(preferencesRepository,repository)
    }

    @Provides
    @Singleton
    fun providesRepository(realm :Realm): Repository {
        return Repository(realm)
    }
}
package com.example.mymap.di.modules

import com.example.mymap.network.ApiRepository
import com.example.mymap.network.ApiRepositoryImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModules {

   @Binds
    abstract fun provideApiRepository(apiRepositoryImp: ApiRepositoryImp): ApiRepository
}
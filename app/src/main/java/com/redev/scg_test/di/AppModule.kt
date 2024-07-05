package com.redev.scg_test.di

import com.redev.scg_test.core.network.APIKeyInterceptor
import com.redev.scg_test.features.news.data.remote.NewRemoteAPI
import com.redev.scg_test.features.news.data.repository.NewRepositoryImpl
import com.redev.scg_test.features.news.data.repository.NewSource
import com.redev.scg_test.features.news.domain.model.Source
import com.redev.scg_test.features.news.domain.repository.NewRepository
import com.redev.scg_test.features.news.domain.use_case.NewUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(APIKeyInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun providerNewApi(okHttpClient: OkHttpClient): NewRemoteAPI {
        return Retrofit.Builder().baseUrl("https://newsapi.org/v2/")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
            .create(NewRemoteAPI::class.java)
    }

    @Provides
    @Singleton
    fun providerNewRepo(api:NewRemoteAPI):NewRepository {
        return NewRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providerNewUseCase(repo:NewRepositoryImpl):NewUseCase {
        return NewUseCase(repo)
    }


}
package com.redev.scg_test.features.news.data.remote

import com.redev.scg_test.BuildConfig
import com.redev.scg_test.features.news.domain.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewRemoteAPI {

    @GET("everything")
    suspend fun fetchNew(
        @Query("q") q:String,
        @Query("page") page:Int,
        @Query("pageSize") pageSize:Int):NewsResponse
}
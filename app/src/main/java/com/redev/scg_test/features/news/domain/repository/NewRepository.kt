package com.redev.scg_test.features.news.domain.repository

import com.redev.scg_test.features.news.domain.model.NewsResponse

interface NewRepository {
    suspend fun fetchNew(source: String,page:Int,pageSize:Int):NewsResponse
}
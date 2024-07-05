package com.redev.scg_test.features.news.domain.use_case

import com.redev.scg_test.features.news.data.repository.NewRepositoryImpl
import javax.inject.Inject

class NewUseCase @Inject constructor(private val repo:NewRepositoryImpl) {
    suspend fun invoke(source: String,page:Int,pageSize:Int) = repo.fetchNew(source,page, pageSize)
}
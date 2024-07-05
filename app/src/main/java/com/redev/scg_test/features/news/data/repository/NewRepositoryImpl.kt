package com.redev.scg_test.features.news.data.repository


import androidx.paging.*
import com.redev.scg_test.features.news.data.remote.NewRemoteAPI
import com.redev.scg_test.features.news.domain.model.Article
import com.redev.scg_test.features.news.domain.repository.NewRepository
import com.redev.scg_test.features.news.domain.use_case.NewUseCase
import javax.inject.Inject

class NewRepositoryImpl @Inject constructor(private val api:NewRemoteAPI) : NewRepository {

    override suspend fun fetchNew(source: String,page:Int,pageSize:Int) =  api.fetchNew(source,page,pageSize)
}

class NewSource (private val useCase:NewUseCase,private val p:String):PagingSource<Int,Article>(){
    override fun getRefreshKey(state: PagingState<Int, Article>): Int? = state.anchorPosition

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
       return try {
            val nextPageNumber = params.key ?: 1
            val response = useCase.invoke(p, nextPageNumber, params.loadSize)

            LoadResult.Page(
                data = response.articles,
                prevKey = params.key,
                nextKey = params.key?.plus(1)
            )
        } catch (e: Exception) {
            println("have error: ${e.localizedMessage}")
            LoadResult.Error(e)
        }
    }

}
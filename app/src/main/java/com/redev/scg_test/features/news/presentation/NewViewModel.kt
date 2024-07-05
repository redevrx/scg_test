package com.redev.scg_test.features.news.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.redev.scg_test.features.news.data.repository.NewSource
import com.redev.scg_test.features.news.domain.model.Article
import com.redev.scg_test.features.news.domain.use_case.NewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewViewModel @Inject constructor(
    private val newUseCase: NewUseCase):ViewModel() {

    private val _newState = MutableStateFlow<UiState>(UiState.Loading)
    val state get() = _newState.asStateFlow()
    val txtSearch = mutableStateOf("tesla")
    private var page = 1
    private val items:MutableList<Article> = mutableListOf()
    private val pageSize = 10

   val newPaging = Pager(
    config = PagingConfig(pageSize, enablePlaceholders = true)
    ) {
        NewSource(newUseCase,txtSearch.value)
    }.flow.cachedIn(viewModelScope)


    fun onSearch() = viewModelScope.launch{
        items.clear()
        page = 1
        _newState.value = UiState.Loading
        val mResponse = newUseCase.invoke(txtSearch.value,page,pageSize)
        items.addAll(mResponse.articles)
        _newState.value = UiState.Success(items)
    }

    fun loadMore() = viewModelScope.launch{
        page+=1

        val mResponse = newUseCase.invoke(txtSearch.value,page,pageSize)
        items.addAll(mResponse.articles)
        _newState.value = UiState.Success(items)
    }

    fun nn() = viewModelScope.launch{

    }
}
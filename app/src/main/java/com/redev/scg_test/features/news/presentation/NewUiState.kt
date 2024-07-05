package com.redev.scg_test.features.news.presentation

import com.redev.scg_test.features.news.domain.model.Article


sealed class UiState {
    data object Loading : UiState()
    data class Success(val data: List<Article>) : UiState()
    data class Error(val message: String) : UiState()
}
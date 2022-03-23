package com.bakharaalief.menuapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakharaalief.menuapp.BuildConfig
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.data.remote.retorfit.ApiConfig
import kotlinx.coroutines.launch

class MainVM(private val menuRepository: MenuRepository) : ViewModel() {

    fun searchMenu(keyword : String) = menuRepository.searchMenu(keyword)
}
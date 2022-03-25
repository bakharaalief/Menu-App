package com.bakharaalief.menuapp.viewModel

import androidx.lifecycle.ViewModel
import com.bakharaalief.menuapp.data.MenuRepository

class MainVM(private val menuRepository: MenuRepository) : ViewModel() {

    fun searchMenu(keyword: String) = menuRepository.searchMenu(keyword)
}
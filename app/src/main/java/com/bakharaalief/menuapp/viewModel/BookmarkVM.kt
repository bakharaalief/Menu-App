package com.bakharaalief.menuapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity

class BookmarkVM(private val menuRepository: MenuRepository) : ViewModel() {

    fun getMenuBookmarked(): LiveData<List<MenuEntity>> = menuRepository.getMenuBookmarked()
}
package com.bakharaalief.menuapp.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity
import kotlinx.coroutines.launch

class DetailVM(private val menuRepository: MenuRepository) : ViewModel() {

    fun getMenuInstructions(id: Int) = menuRepository.menuInstructions(id)

    fun getMenuIsBookmarked(id: Int) : LiveData<Boolean> = menuRepository.isMenuBookmarked(id)

    fun saveMenu(menuEntity: MenuEntity){
        viewModelScope.launch {
            menuRepository.insertMenu(menuEntity)
        }
    }

    fun removeMenu(menuEntity: MenuEntity){
        viewModelScope.launch {
            menuRepository.deleteMenu(menuEntity)
        }
    }
}
package com.bakharaalief.menuapp.viewModel

import androidx.lifecycle.ViewModel
import com.bakharaalief.menuapp.data.MenuRepository

class DetailVM(private val menuRepository: MenuRepository) : ViewModel() {

    fun getMenuInstructions(id: Int) = menuRepository.menuInstructions(id)
}
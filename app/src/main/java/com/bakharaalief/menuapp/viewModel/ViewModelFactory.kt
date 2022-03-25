package com.bakharaalief.menuapp.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.di.Injection

class ViewModelFactory private constructor(
    private val menuRepository: MenuRepository,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainVM::class.java) -> MainVM(menuRepository) as T
            modelClass.isAssignableFrom(DetailVM::class.java) -> DetailVM(menuRepository) as T
            modelClass.isAssignableFrom(BookmarkVM::class.java) -> BookmarkVM(menuRepository) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideRepository(context),
                )
            }.also { instance = it }
    }
}
package com.bakharaalief.menuapp.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bakharaalief.menuapp.adapter.MenuListAdapter
import com.bakharaalief.menuapp.data.Result
import com.bakharaalief.menuapp.data.remote.response.ResultsItem
import com.bakharaalief.menuapp.databinding.ActivityMainBinding
import com.bakharaalief.menuapp.ui.detail.DetailActivity
import com.bakharaalief.menuapp.viewModel.MainVM
import com.bakharaalief.menuapp.viewModel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainVM> {
        ViewModelFactory.getInstance(this)
    }

    private val defaultSearch = "chicken"
    private val sizeGrid = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init rv
        binding.menuRv.layoutManager = GridLayoutManager(this, sizeGrid)
        binding.menuRv.adapter = MenuListAdapter()

        viewModel.searchMenu(defaultSearch).observe(this) { status ->
            when (status) {
                is Result.Loading -> setLoadingIndicator(true)
                is Result.Success -> {
                    setLoadingIndicator(false)
                    setUpdateRv(status.data)
                }
                is Result.Error -> setLoadingIndicator(false)
            }
        }
    }

    private fun setUpdateRv(listMenus: List<ResultsItem>) {
        val adapter = MenuListAdapter()
        adapter.submitList(listMenus)
        adapter.setOnItemClickCallback(object : MenuListAdapter.OnItemClickCallback {
            override fun onClick(resultsItem: ResultsItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DETAIL_DATA, resultsItem)
                startActivity(intent)
            }
        })

        binding.menuRv.adapter = adapter
    }

    private fun setLoadingIndicator(loading: Boolean) {
        binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
    }
}
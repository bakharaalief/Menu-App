package com.bakharaalief.menuapp.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import com.bakharaalief.menuapp.R
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

        //set action bar
        setActionBar()

        //init rv
        binding.menuRv.layoutManager = GridLayoutManager(this, sizeGrid)
        binding.menuRv.adapter = MenuListAdapter()

        //search menu with default keyword
        searchMenu(defaultSearch)
    }

    private fun setActionBar() {
        setSupportActionBar(binding.mainToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu_main, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                searchMenu(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        return true
    }

    private fun searchMenu(keyword: String) {
        viewModel.searchMenu(keyword).observe(this) { status ->
            when (status) {
                is Result.Loading -> setLoadingIndicator(true)
                is Result.Success -> {
                    setLoadingIndicator(false)
                    setUpdateRv(status.data)
                }
                is Result.Error -> {
                    setLoadingIndicator(false)
                    Toast.makeText(this, status.message, Toast.LENGTH_SHORT).show()
                }
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
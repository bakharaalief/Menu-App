package com.bakharaalief.menuapp.ui.bookmark

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bakharaalief.menuapp.adapter.MenuListAdapter
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity
import com.bakharaalief.menuapp.databinding.ActivityBookmarkBinding
import com.bakharaalief.menuapp.ui.detail.DetailActivity
import com.bakharaalief.menuapp.viewModel.BookmarkVM
import com.bakharaalief.menuapp.viewModel.ViewModelFactory

class BookmarkActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBookmarkBinding

    private val viewModel by viewModels<BookmarkVM> {
        ViewModelFactory.getInstance(this)
    }

    private val sizeGrid = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBookmarkBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //init rv
        binding.bookmarkRv.layoutManager = GridLayoutManager(this, sizeGrid)
        binding.bookmarkRv.adapter = MenuListAdapter()

        //set action bar
        setActionBar()

        //get menu
        getMenu()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.bookmarkToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun getMenu() {
        viewModel.getMenuBookmarked().observe(this) { data ->
            setUpdateRv(data)
        }
    }

    private fun setUpdateRv(listMenus: List<MenuEntity>) {
        val adapter = MenuListAdapter()
        adapter.submitList(listMenus)
        adapter.setOnItemClickCallback(object : MenuListAdapter.OnItemClickCallback {
            override fun onClick(menuEntity: MenuEntity) {
                val intent = Intent(this@BookmarkActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.DETAIL_DATA, menuEntity)
                startActivity(intent)
            }
        })

        binding.bookmarkRv.adapter = adapter
    }
}
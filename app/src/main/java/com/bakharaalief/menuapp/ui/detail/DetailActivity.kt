package com.bakharaalief.menuapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bakharaalief.menuapp.data.remote.response.ResultsItem
import com.bakharaalief.menuapp.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var menuData: ResultsItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data
        menuData = intent.getParcelableExtra<ResultsItem>(DETAIL_DATA) as ResultsItem

        //setData
        setActionBar()
        setPhotoMenu()
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
        setSupportActionBar(binding.detailToolbar)
        supportActionBar?.title = menuData.title
    }

    private fun setPhotoMenu() {
        Glide
            .with(this)
            .load(menuData.image)
            .into(binding.menuPhotoToolbar)
    }

    companion object {
        const val DETAIL_DATA = "detail_data"
    }
}
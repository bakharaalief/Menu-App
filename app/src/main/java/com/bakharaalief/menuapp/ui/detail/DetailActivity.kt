package com.bakharaalief.menuapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakharaalief.menuapp.R
import com.bakharaalief.menuapp.adapter.InstructionListAdapter
import com.bakharaalief.menuapp.data.Result
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity
import com.bakharaalief.menuapp.data.remote.response.StepsItem
import com.bakharaalief.menuapp.databinding.ActivityDetailBinding
import com.bakharaalief.menuapp.viewModel.DetailVM
import com.bakharaalief.menuapp.viewModel.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var menuData: MenuEntity

    private val viewModel by viewModels<DetailVM> {
        ViewModelFactory.getInstance(this)
    }

    private var isBookmarked: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data
        menuData = intent.getParcelableExtra<MenuEntity>(DETAIL_DATA) as MenuEntity

        //setData
        setActionBar()
        setPhotoMenu()

        //init rv
        binding.instructionsRv.layoutManager = LinearLayoutManager(this)
        binding.instructionsRv.adapter = InstructionListAdapter()

        //search Instruction by id
        viewModel.getMenuInstructions(menuData.id).observe(this) { status ->
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

        //check is bookmarked or not
        viewModel.getMenuIsBookmarked(menuData.id).observe(this) { status ->
            isBookmarked = status
            setBookmarkedIcon(status)
        }

        //floating button
        binding.floatingActionButton.setOnClickListener {
            val message = if (isBookmarked) {
                viewModel.removeMenu(menuData)
                "Berhasil remove menu"
            } else {
                viewModel.saveMenu(menuData)
                "Berhasil tambah menu"
            }

            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
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
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(binding.menuPhotoToolbar)
    }

    private fun setUpdateRv(listInstructions: List<StepsItem>) {
        val adapter = InstructionListAdapter()
        adapter.submitList(listInstructions)
        binding.instructionsRv.adapter = adapter
    }

    private fun setLoadingIndicator(loading: Boolean) {
        binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setBookmarkedIcon(status: Boolean) {
        val drawable =
            if (status) R.drawable.ic_baseline_bookmark_24 else R.drawable.ic_baseline_bookmark_border_24
        binding.floatingActionButton.setImageResource(drawable)
    }

    companion object {
        const val DETAIL_DATA = "detail_data"
    }
}
package com.bakharaalief.menuapp.ui.detail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bakharaalief.menuapp.adapter.InstructionListAdapter
import com.bakharaalief.menuapp.data.Result
import com.bakharaalief.menuapp.data.remote.response.ResultsItem
import com.bakharaalief.menuapp.data.remote.response.StepsItem
import com.bakharaalief.menuapp.databinding.ActivityDetailBinding
import com.bakharaalief.menuapp.viewModel.DetailVM
import com.bakharaalief.menuapp.viewModel.ViewModelFactory
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private lateinit var menuData: ResultsItem

    private val viewModel by viewModels<DetailVM> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //get data
        menuData = intent.getParcelableExtra<ResultsItem>(DETAIL_DATA) as ResultsItem

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

    private fun setUpdateRv(listInstructions: List<StepsItem>) {
        val adapter = InstructionListAdapter()
        adapter.submitList(listInstructions)
        binding.instructionsRv.adapter = adapter
    }

    private fun setLoadingIndicator(loading: Boolean) {
        binding.loadingIndicator.visibility = if (loading) View.VISIBLE else View.GONE
    }

    companion object {
        const val DETAIL_DATA = "detail_data"
    }
}
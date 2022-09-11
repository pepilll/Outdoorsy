package com.example.outdoorsy.util

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class PaginationListener(
    private val layoutManager: LinearLayoutManager,
    private val pageSize: Int
) : RecyclerView.OnScrollListener() {
    protected abstract fun loadMoreItems()
    abstract val isLastPage: Boolean
    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (
            !isLoading
            && !isLastPage
            && visibleItemCount + firstVisibleItemPosition >= totalItemCount
            && firstVisibleItemPosition >= 0
            && totalItemCount >= pageSize
        ) {
            loadMoreItems()
        }
    }
}
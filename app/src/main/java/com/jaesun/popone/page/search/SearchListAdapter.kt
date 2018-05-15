package com.jaesun.popone.page.search

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.jaesun.popone.R
import com.jaesun.popone.data.Document
import com.jaesun.popone.data.Result
import kotlinx.android.synthetic.main.item_image.view.*

class SearchListAdapter(val result : Result,
                        val presenter : SearchContract.Presenter,
                        val search : String) : RecyclerView.Adapter<SearchListAdapter.SearchListViewHolder>() {

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : SearchListViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
            .let { SearchListViewHolder(it) }
    }

    override fun getItemCount() : Int {
        return result.documents.size
    }

    override fun onBindViewHolder(holder : SearchListViewHolder, position : Int) {
        holder.setItem(result.documents[position])
    }

    fun addList(result : Result) {
        this.result.meta = result.meta
        this.result.documents.addAll(result.documents)
        notifyItemRangeInserted(itemCount, this.result.documents.size)
    }

    inner class SearchListViewHolder(val view : View) : RecyclerView.ViewHolder(view) {
        fun setItem(document : Document) {
            view.setOnClickListener {
                presenter.clickItem(document)
            }
            Glide.with(view.context)
                .load(document.image_url)
                .into(view.item_image)
        }
    }

}
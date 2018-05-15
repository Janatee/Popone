package com.jaesun.popone.page.search

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jaesun.popone.BaseActivity
import com.jaesun.popone.R
import com.jaesun.popone.data.Document
import com.jaesun.popone.data.Result
import com.jaesun.popone.page.detail.DetailActivity
import com.jaesun.popone.util.EXTRA_DOCUMENT
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : BaseActivity(), SearchContract.View {

    override lateinit var presenter : SearchContract.Presenter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        presenter = SearchPresenter(this).apply {
            compositeDisposable = CompositeDisposable()
        }
        presenter.create()
        // 검색 버튼
        presenter.clickSearch(search_btn, search_edit)
        // 검색결과 리스트 스크롤
        presenter.scrollToLoadMore(search_list)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }

    // 검색 결과
    override fun showSearchList(result : Result, search : String) {
        hideKeyboard()
        if (result.documents.size == 0) {
            showEmpty()
            return
        }
        search_list.visibility = View.VISIBLE
        search_empty.visibility = View.GONE
        val adapter = SearchListAdapter(result, presenter, search)
        search_list.adapter = adapter
    }

    // 이미지 없을 경우
    override fun showEmpty() {
        hideKeyboard()
        search_list.visibility = View.GONE
        search_empty.visibility = View.VISIBLE
    }

    // 검색결과 추가
    override fun addSearchList(result : Result) {
        val adapter = search_list.adapter as SearchListAdapter
        adapter.addList(result)
    }

    // 사진 전체화면으로 보기 페이지 이동
    override fun startDetailActivity(document : Document) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_DOCUMENT, document.image_url)
        startActivity(intent)
    }

    // 키보드 숨김
    private fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(search_edit.windowToken, 0)
    }
}
package com.jaesun.popone.page.search

import android.support.v7.widget.RecyclerView
import android.widget.EditText
import com.jaesun.popone.BasePresenter
import com.jaesun.popone.BaseView
import com.jaesun.popone.data.Document
import com.jaesun.popone.data.Result

interface SearchContract {
    interface View : BaseView<Presenter> {
        /**
         * 검색 결과 보여줌
         * @param result 검색 결과
         * @param search 검색어
         */
        fun showSearchList(result : Result, search : String)

        /**
         * 검색 결과 추가
         * @param result 검색 결과
         */
        fun addSearchList(result : Result)

        /**
         * 검색결과 없음
         */
        fun showEmpty()

        /**
         * 사진 전체화면으로 보기 페이지 이동
         * @param document 이미지정보
         */
        fun startDetailActivity(document : Document)
    }

    interface Presenter : BasePresenter {
        /**
         * 버튼 클릭
         * @param btnView 버튼
         * @param editText 검색창
         */
        fun clickSearch(btnView : android.view.View, editText : EditText)

        /**
         * 검색 이미지 클릭
         * @param document 이미지정보
         */
        fun clickItem(document : Document)

        /**
         * 스크롤.
         * @param recyclerView RecyclerView
         */
        fun scrollToLoadMore(recyclerView : RecyclerView)
    }
}
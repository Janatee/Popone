package com.jaesun.popone.page.search

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.EditText
import com.jaesun.popone.BuildConfig
import com.jaesun.popone.data.Document
import com.jaesun.popone.data.Result
import com.jaesun.popone.network.DaggerNetworkComponent
import com.jaesun.popone.util.SIZE
import com.jaesun.popone.util.SORT_ACCURACY
import com.jaesun.popone.util.THROTTLE_TIME
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchPresenter(val view : SearchContract.View) : SearchContract.Presenter {

    override lateinit var compositeDisposable : CompositeDisposable

    override fun create() {
        // Nothing to do...
    }

    override fun destroy() {
        compositeDisposable.clear()
    }

    // 검색 버튼 클릭
    override fun clickSearch(btnView : View, editText : EditText) {
        RxView.clicks(btnView)
            .throttleFirst(THROTTLE_TIME, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .flatMapSingle { searchImage(editText.text.toString(), SORT_ACCURACY, 1, SIZE) } // 검색 결과 요청. 1페이지 부터
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // 검색 결과 갱신
                view.showSearchList(it, editText.text.toString())
            }, {
                // 에러 났을 경우. 이미지 없다는 화면 보여줌
                it.printStackTrace()
                view.showEmpty()
            })
            .addTo(compositeDisposable)
    }

    // 검색 결과 아이템 선택
    override fun clickItem(document : Document) {
        view.startDetailActivity(document)
    }

    // 검색 결과 리스트 스크롤
    override fun scrollToLoadMore(recyclerView : RecyclerView) {
        RxRecyclerView.scrollEvents(recyclerView)
            .filter { !(recyclerView.adapter as SearchListAdapter).result.meta.is_end } // 마지막 검색 결과인지 여부 확인
            .map { it.view().layoutManager as LinearLayoutManager }
            .filter { it.itemCount - 1 == it.findLastCompletelyVisibleItemPosition() } // 리스트의 마지막 까지 스크롤 했는지 확인
            .observeOn(Schedulers.io())
            .flatMapSingle {
                // position계산 후 새로운 페이지로 이미지 검색 결과 요청
                val page = (it.findLastCompletelyVisibleItemPosition() / SIZE) + 2
                searchImage((recyclerView.adapter as SearchListAdapter).search, SORT_ACCURACY, page, SIZE)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // 검색 결과 추가
                view.addSearchList(it)
            }, {
                // 에러났을 경우.
                it.printStackTrace()
            })
            .addTo(compositeDisposable)
    }

    // 검색 요청후 결과 리턴
    private fun searchImage(search : String,
                            sort : String,
                            page : Int,
                            size : Int) : Single<Result> {
        return DaggerNetworkComponent.create()
            .kakaoApi()
            .imageList(BuildConfig.REST_API_KEY, search, sort, page, size)
    }
}
package com.jaesun.popone.page.detail

import io.reactivex.disposables.CompositeDisposable

class DetailPresenter(val view : DetailContract.View) : DetailContract.Presenter {
    override lateinit var compositeDisposable : CompositeDisposable

    override fun create() {
    }

    override fun destroy() {
        compositeDisposable.clear()
    }
}
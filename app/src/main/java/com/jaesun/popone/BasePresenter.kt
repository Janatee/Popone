package com.jaesun.popone

import io.reactivex.disposables.CompositeDisposable

interface BasePresenter {
    var compositeDisposable : CompositeDisposable
    fun create()
    fun destroy()
}
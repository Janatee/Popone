package com.jaesun.popone

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var compositeDisposable : CompositeDisposable

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        compositeDisposable = CompositeDisposable()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    fun getContext() : Context {
        return this
    }
}
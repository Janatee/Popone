package com.jaesun.popone

import android.content.Context

interface BaseView<T> {
    var presenter : T
    fun getContext() : Context
}
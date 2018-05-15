package com.jaesun.popone.page.detail

import com.jaesun.popone.BasePresenter
import com.jaesun.popone.BaseView

interface DetailContract {
    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {
    }
}
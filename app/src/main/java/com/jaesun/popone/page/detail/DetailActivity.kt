package com.jaesun.popone.page.detail

import android.os.Bundle
import com.bumptech.glide.Glide
import com.jaesun.popone.BaseActivity
import com.jaesun.popone.R
import com.jaesun.popone.util.EXTRA_DOCUMENT
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity(), DetailContract.View {
    override lateinit var presenter : DetailContract.Presenter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        presenter = DetailPresenter(this).apply {
            compositeDisposable = CompositeDisposable()
        }
        presenter.create()

        // 이미지 보여줌
        Glide.with(this).load(intent.getStringExtra(EXTRA_DOCUMENT)).into(detail_image)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}
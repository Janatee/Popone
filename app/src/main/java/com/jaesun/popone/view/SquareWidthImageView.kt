package com.jaesun.popone.view

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * 정사각형 이미지 뷰
 */
class SquareWidthImageView : ImageView {

    constructor(context : Context?) : super(context)
    constructor(context : Context?, attrs : AttributeSet?) : super(context, attrs)
    constructor(context : Context?, attrs : AttributeSet?, defStyleAttr : Int) : super(context,
        attrs,
        defStyleAttr)

    override fun onMeasure(widthMeasureSpec : Int, heightMeasureSpec : Int) {
        // 가로 크기에 맞춘다.
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}
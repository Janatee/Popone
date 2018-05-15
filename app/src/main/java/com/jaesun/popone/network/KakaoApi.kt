package com.jaesun.popone.network

import com.jaesun.popone.data.Result
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

const val ENDPOINT = "https://dapi.kakao.com"

interface KakaoApi {

    @GET("/v2/search/image")
    fun imageList(@Header("Authorization") key : String, @Query("query") query : String, @Query("sort") sort : String, @Query("page") page : Int, @Query("size") size : Int) : Single<Result>

}
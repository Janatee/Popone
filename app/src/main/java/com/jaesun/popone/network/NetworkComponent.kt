package com.jaesun.popone.network

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun kakaoApi() : KakaoApi
}
package com.zerdaket.example

import android.app.Application
import kotlin.properties.Delegates

/**
 * @author zerdaket
 * @date 2021/12/6 10:48 下午
 */
class App: Application() {

    companion object {
        private var instance: App by Delegates.notNull()
        fun instance() = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
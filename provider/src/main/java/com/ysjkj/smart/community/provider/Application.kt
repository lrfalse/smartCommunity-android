package com.ysjkj.smart.community.provider

import android.content.Context
import android.support.multidex.MultiDex
import com.piaolac.core.base.BaseApplication
import com.ysjkj.smart.community.umeng.ConfigUtils

/**
 * Created by yangqiang on 2018/4/2.
 */
class Application : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        ConfigUtils.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
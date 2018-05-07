package com.ysjkj.smart.community.provider.base

import android.content.Context
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.Utils
import com.mob.MobSDK
import com.piaolac.core.base.BaseApplication
import com.ysjkj.smart.community.provider.model.User
import com.ysjkj.smart.community.umeng.ConfigUtils

/**
 * Created by yangqiang on 2018/4/2.
 */
class Application : BaseApplication() {

    companion object {
        var user: User? = null
    }

    override fun onCreate() {
        super.onCreate()
        ConfigUtils.init(this)
        MobSDK.init(this)
        Utils.init(this)
        LogUtils.getConfig().setGlobalTag("okhttp").setBorderSwitch(false)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
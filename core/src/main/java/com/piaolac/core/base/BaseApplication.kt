package com.piaolac.core.base

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * Created by yangqiang on 2018/3/14.
 */
abstract class BaseApplication : Application() {

    companion object {
        lateinit var context: BaseApplication
        var IS_DEBUG = true
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        initARouter()
    }


    /**
     * 阿里路由初始化
     */
    private fun initARouter() {
        if (IS_DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()    // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)
    }


}
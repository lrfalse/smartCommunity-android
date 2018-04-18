package com.ysjkj.smart.community.provider.router

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * 用户登录拦截器
 * @author yangqiang
 * @create 2018-04-09 上午11:21
 **/
@Interceptor(priority = 1, name = "用户登录拦截器")
class UserInterceptor : IInterceptor {
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        Log.d("xxx","拦截器触发了${postcard?.group}")
//        if(!postcard?.group.equals("userCenter",true)){
//            router(RouterPath.UserCenter.LOGIN).navigation()
//        }else{
//            callback?.onContinue(postcard)
//        }
        callback?.onContinue(postcard)
    }

    override fun init(context: Context?) {
    }
}
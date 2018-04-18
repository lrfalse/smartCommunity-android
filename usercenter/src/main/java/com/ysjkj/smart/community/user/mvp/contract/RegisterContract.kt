package com.ysjkj.smart.community.user.mvp.contract

import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * 注册界面接口
 * @author yangqiang
 * @create 2018-04-18 上午11:28
 **/
interface RegisterContract {
    interface View : IView {
        fun onSendCodeResult(result: Model.Result)
        fun onSubmitCodeResult(result: Model.Result)
    }


    class Model : IModel {

        data class Result(var isSuccess: Boolean, var data: Any)

        /**
         * 发送验证码
         */
        fun sendCode(phone: String, country: String = "86"): Observable<Result> {
            return Observable.create<Result> {
                SMSSDK.registerEventHandler(object : EventHandler() {
                    override fun afterEvent(event: Int, result: Int, data: Any) {

                        if (result == SMSSDK.RESULT_COMPLETE) {
                            it.onNext(Result(true, data))
                        } else {
                            it.onNext(Result(false, data))
                        }
                        it.onComplete()
                        SMSSDK.unregisterEventHandler(this)
                    }
                })
                SMSSDK.getVerificationCode(country, phone)
            }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread())
        }

        /**
         * 提交验证码
         */
        fun submitCode(code: String, phone: String, country: String = "86"): Observable<Result> {
            return Observable.create<Result> {
                // 注册一个事件回调，用于处理提交验证码操作的结果
                SMSSDK.registerEventHandler(object : EventHandler() {
                    override fun afterEvent(event: Int, result: Int, data: Any) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            it.onNext(Result(true, data))
                        } else {
                            it.onNext(Result(false, data))
                        }
                        it.onComplete()
                        SMSSDK.unregisterEventHandler(this)
                    }
                })
                // 触发操作
                SMSSDK.submitVerificationCode(country, phone, code)
            }.observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread())
        }
    }
}
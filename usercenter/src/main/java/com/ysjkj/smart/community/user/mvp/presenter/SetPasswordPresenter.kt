package com.ysjkj.smart.community.user.mvp.presenter

import com.ysjkj.smart.community.provider.base.ProviderPresenter
import com.ysjkj.smart.community.provider.transformer.LoadingTransformer
import com.ysjkj.smart.community.user.mvp.contract.SetPasswordContract

/**
 * 设置密码
 * @author yangqiang
 * @create 2018-04-27 上午11:38
 **/
class SetPasswordPresenter : ProviderPresenter<SetPasswordContract.Model, SetPasswordContract.View>() {

    /**
     * 注册
     */
    fun register(phone: String, pwd: String, code: String) {
        model {
            register(phone, pwd, code).execute(LoadingTransformer(context)) {
                onNext {
                    view {
                        registerCallback(it)
                    }
                }
            }
        }
    }

}
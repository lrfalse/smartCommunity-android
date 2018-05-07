package com.ysjkj.smart.community.user.mvp.presenter

import com.ysjkj.smart.community.provider.base.ProviderPresenter
import com.ysjkj.smart.community.provider.transformer.LoadingTransformer
import com.ysjkj.smart.community.user.mvp.contract.LoginContract

/**
 * 登录
 * @author yangqiang
 * @create 2018-04-27 下午10:24
 **/
class LoginPresenter : ProviderPresenter<LoginContract.Model, LoginContract.View>() {

    /**
     * 手机登录
     */
    fun phoneLogin(phone: String, pwd: String) {
        model {
            login(mapOf("mobPhone" to phone, "pwd" to pwd, "token" to "P"))
                    .execute(LoadingTransformer(context)) {
                        onNext {
                            view {
                                loginResult(it)
                            }
                        }
                    }
        }
    }


    /**
     * 第三方登录
     */
    fun authLogin(openid: String?, name: String?, sex: String?, headUrl: String?, token: String) {
        model {
            login(mapOf(if ("W" == token) "wopenId" to "$openid" else "qopenId" to "$openid", "name" to "$name", "sex" to "$sex", "imageUrl" to "$headUrl", "token" to token))
                    .execute(LoadingTransformer(context)) {
                        onNext {
                            view {
                                loginResult(it)
                            }
                        }
                    }
        }
    }
}
package com.ysjkj.smart.community.user.mvp.presenter

import com.ysjkj.smart.community.provider.base.ProviderPresenter
import com.ysjkj.smart.community.provider.transformer.LoadingTransformer
import com.ysjkj.smart.community.user.mvp.contract.RegisterContract

/**
 * 注册
 * @author yangqiang
 * @create 2018-04-18 上午11:26
 **/
class RegisterPresenter : ProviderPresenter<RegisterContract.Model, RegisterContract.View>() {

    /**
     * 发送验证码
     */
    fun sendCode(phone: String) {
        model {
            sendCode(phone).execute(LoadingTransformer(context)) {
                onNext {
                    view {
                        onSendCodeResult(it)
                    }
                }
            }
        }
    }

    /**
     * 提交验证码
     */
    fun submitCode(code: String, phone: String) {
        model {
            submitCode(code, phone).execute(LoadingTransformer(context)) {
                onNext {
                    view {
                        onSubmitCodeResult(it)
                    }
                }
            }
        }
    }
}
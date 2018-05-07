package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.jump
import com.piaolac.core.ext.trimValue
import com.piaolac.core.utils.RxUtils
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import com.ysjkj.smart.community.user.mvp.contract.RegisterContract
import com.ysjkj.smart.community.user.mvp.presenter.RegisterPresenter
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.user_activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit


/**
 * 用户注册
 */
@Route(path = RouterPath.UserCenter.REGISTER_ACCOUNT)
class RegisterActivity : BaseActivity<RegisterPresenter>(), RegisterContract.View {
    companion object {
        const val PARAM_PHONE_NUMBER = "PHONE_NUMBER"
        const val PARAM_CODE = "PARAM_CODE"
    }

    /**
     * 验证码发送返回
     */
    override fun onSendCodeResult(result: RegisterContract.Model.Result) {
        if (result.isSuccess) {
            toast("发送验证码成功")
            loopValidaDisplay()
        } else {
            toast("发送验证码失败")
        }
    }

    /**
     * 验证码提交返回
     */
    override fun onSubmitCodeResult(result: BaseResponse<String>) {

        if (result.isSuccess()) {
            jump(RouterPath.UserCenter.SET_PASSWORD) {
                withString(PARAM_PHONE_NUMBER, et_phone.trimValue())
                withString(PARAM_CODE, et_code.trimValue())
            }
            finish()
        } else {
            toast(result.statusMsg)
        }
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_register).withToolbar("", true, unLine = false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        fbtn_next.onClick {

            if (checkPhone() && checkCode()) {
                presenter {
                    submitCode(et_code.trimValue(), et_phone.trimValue())
                }
            }

        }

        btn_valida_code.onClick {

            if (checkPhone()) {
                presenter {
                    sendCode(et_phone.trimValue())
                }
            }
        }
    }

    /**
     * 验证手机号码
     */
    fun checkPhone(): Boolean {
        var phone = et_phone.trimValue()
        if (phone.isNullOrEmpty()) {
            toast("请输入手机号码")
            return false
        }

        if (phone.length != 11) {
            toast("请输入真确的手机号码")
            return false
        }

        return true
    }

    /**
     * 验证验证码
     */
    fun checkCode(): Boolean {
        var code = et_code.trimValue()
        if (code.isNullOrEmpty()) {
            toast("请输入验证码 ")
            return false
        }
        return true
    }


    /**
     * 刷新获取验证码按钮
     */
    private fun loopValidaDisplay() {
        val count = 60L
        Observable.intervalRange(0, count + 1, 0, 1, TimeUnit.SECONDS)
                .compose(RxUtils.androidTransformer()).subscribeBy(onNext = {
                    btn_valida_code.apply {
                        text = "${count - it}" + "s"
                        isEnabled = false
                    }
                }, onComplete = {
                    btn_valida_code.apply {
                        text = "获取验证码"
                        isEnabled = true
                    }
                })
    }

}

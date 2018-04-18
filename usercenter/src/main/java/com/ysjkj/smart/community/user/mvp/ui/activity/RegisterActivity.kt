package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.trimValue
import com.piaolac.core.utils.RxUtils
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
    override fun onSendCodeResult(result: RegisterContract.Model.Result) {
        toast("发送验证码:${result.isSuccess} ${result.data}")
        if (result.isSuccess) {
            loopValidaDisplay()
        }
    }

    override fun onSubmitCodeResult(result: RegisterContract.Model.Result) {
        toast("验证验证码:${result.isSuccess} ${result.data}")
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_register).withToolbar("", true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        floating_action_button.onClick {

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
        val count = 5L
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

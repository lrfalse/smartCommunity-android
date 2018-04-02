package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.piaolac.core.utils.RxUtils
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.user_activity_register.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import java.util.concurrent.TimeUnit

/**
 * 用户注册
 */
@Route(path = RouterPath.UserCenter.REGISTER_ACCOUNT)
class RegisterActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_register).withToolbar("", true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        floating_action_button.onClick {
            router(RouterPath.UserCenter.SET_PASSWORD).navigation()
        }
        btn_valida_code.onClick {
            loopValidaDisplay()
        }
    }


    /**
     * 刷新获取验证码按钮
     */
    private fun loopValidaDisplay() {
        val count = 5L
        Observable.intervalRange(0, count+1, 0, 1, TimeUnit.SECONDS)
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

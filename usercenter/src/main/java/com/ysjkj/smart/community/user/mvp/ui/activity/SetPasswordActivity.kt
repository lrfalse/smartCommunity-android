package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import kotlinx.android.synthetic.main.user_activity_set_password.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange

@Route(path = RouterPath.UserCenter.SET_PASSWORD)
class SetPasswordActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_set_password).withToolbar("", true)
    }

    override fun initView(savedInstanceState: Bundle?) {
        et_password.onFocusChange { v, hasFocus ->
            if (hasFocus) {
                txl_password.hint = "密码长度至少8个字符"
            } else {
                txl_password.hint = "设置密码"
            }
        }

        floating_action_btn.onClick {
            router(RouterPath.UserCenter.CHOOSE_COMMUNIT).navigation()
        }
    }


}

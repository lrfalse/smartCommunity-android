package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.router
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import kotlinx.android.synthetic.main.user_activity_welcome.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 欢迎界面（选择注册、或者登录）
 */
class WelcomeActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_welcome).withToolbar("", menuId = R.menu.user_menu_welcome_break)
    }

    override fun initView(savedInstanceState: Bundle?) {
        btn_user_login.onClick {
            router(RouterPath.UserCenter.FORGET_PASSWORD).navigation()
        }
        btn_user_register.onClick {
            router(RouterPath.UserCenter.REGISTER_ACCOUNT).navigation()
        }
    }
}

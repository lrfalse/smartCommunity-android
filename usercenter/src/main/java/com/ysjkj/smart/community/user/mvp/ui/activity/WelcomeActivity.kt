package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import android.text.Html
import android.view.MenuItem
import com.alibaba.android.arouter.facade.annotation.Route
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
@Route(path = RouterPath.UserCenter.WELCOME)
class WelcomeActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_welcome).withToolbar("", menuId = R.menu.user_menu_welcome_break, unLine = false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        btn_user_login.onClick {
            router(RouterPath.UserCenter.LOGIN).navigation()
        }
        btn_user_register.onClick {
            router(RouterPath.UserCenter.REGISTER_ACCOUNT).navigation()
        }
        tx_protocol.text = Html.fromHtml(resources.getString(R.string.login_protocol))
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return super.onMenuItemClick(item)
    }
}

package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import com.ysjkj.smart.community.user.mvp.contract.LoginContract
import kotlinx.android.synthetic.main.user_activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * 用户登录
 */
@Route(path = RouterPath.UserCenter.FORGET_PASSWORD)
class LoginActivity : BaseActivity<EmptyPresenter>(), LoginContract.View {


    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_login).withToolbar(showBack = true, menuId = R.menu.user_menu_login)
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_weichart.onClick {
        }
    }
}
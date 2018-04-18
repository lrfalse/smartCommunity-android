package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import android.util.Log
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.umeng.socialize.bean.SHARE_MEDIA
import com.ysjkj.smart.community.provider.base.BaseUMengActivity
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.umeng.UMengCode
import com.ysjkj.smart.community.umeng.authLogin
import com.ysjkj.smart.community.user.R
import com.ysjkj.smart.community.user.mvp.contract.LoginContract
import kotlinx.android.synthetic.main.user_activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * 用户登录
 */
@Route(path = RouterPath.UserCenter.LOGIN)
class LoginActivity : BaseUMengActivity<EmptyPresenter>(), LoginContract.View {


    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_login).withToolbar(showBack = true, menuId = R.menu.user_menu_login)
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_weichart.onClick {
            authLogin(SHARE_MEDIA.WEIXIN).subscribe {
                if (it.code == UMengCode.SUCCESS) {
                    Log.d("xxx",it.toString())
                    toast("微信登录成功")
//                    router(RouterPath.Main.HOME).navigation()
                } else {
                    toast("微信登录失败")
                }
            }
        }

        iv_qq.onClick {
            authLogin(SHARE_MEDIA.QQ).subscribe{
                Log.d("xxx",it.toString())
                if (it.code == UMengCode.SUCCESS) {
                    toast("QQ登录成功")

//                    router(RouterPath.Main.HOME).navigation()
                } else {
                    toast("QQ登录失败")
                }
            }
        }
    }
}
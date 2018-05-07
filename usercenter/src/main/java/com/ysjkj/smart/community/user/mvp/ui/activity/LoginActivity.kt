package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.jump
import com.piaolac.core.ext.trimValue
import com.umeng.socialize.bean.SHARE_MEDIA
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.base.BaseUMengActivity
import com.ysjkj.smart.community.provider.model.User
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.umeng.UMengCode
import com.ysjkj.smart.community.umeng.authLogin
import com.ysjkj.smart.community.user.R
import com.ysjkj.smart.community.user.mvp.contract.LoginContract
import com.ysjkj.smart.community.user.mvp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.user_activity_login.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

/**
 * 用户登录
 */
@Route(path = RouterPath.UserCenter.LOGIN)
class LoginActivity : BaseUMengActivity<LoginPresenter>(), LoginContract.View {
    override fun loginResult(result: BaseResponse<User>) {
        if (result.isSuccess()) {
            jump(RouterPath.Main.HOME)
//            jump(RouterPath.UserCenter.CHOOSE_COMMUNIT)
            finish()
        }
    }


    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_login).withToolbar(showBack = true, menuId = R.menu.user_menu_login, unLine = false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        iv_weichart.onClick {
            authLogin(SHARE_MEDIA.WEIXIN).subscribe {
                if (it.code == UMengCode.SUCCESS) {
                    presenter {
                        authLogin(it.data["openid"], it.data["screen_name"], it.data["gender"], it.data["iconurl"], "W")
                    }
                } else {
                    toast("微信登录失败")
                }
            }
        }

        iv_qq.onClick {
            authLogin(SHARE_MEDIA.QQ).subscribe {
                if (it.code == UMengCode.SUCCESS) {
                    presenter {
                        authLogin(it.data["openid"], it.data["screen_name"], it.data["gender"], it.data["iconurl"], "Q")
                    }
                } else {
                    toast("QQ登录失败")
                }
            }
        }
        fbtn_next.onClick {
            presenter {
                phoneLogin(et_phone.trimValue(), et_password.trimValue())
            }
        }
    }
}
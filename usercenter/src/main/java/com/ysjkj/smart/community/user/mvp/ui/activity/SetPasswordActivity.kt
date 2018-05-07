package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.ext.jump
import com.piaolac.core.ext.trimValue
import com.ysjkj.smart.community.provider.base.Application
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.model.User
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import com.ysjkj.smart.community.user.mvp.contract.SetPasswordContract
import com.ysjkj.smart.community.user.mvp.presenter.SetPasswordPresenter
import kotlinx.android.synthetic.main.user_activity_set_password.*
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.sdk25.coroutines.onFocusChange
import org.jetbrains.anko.toast

@Route(path = RouterPath.UserCenter.SET_PASSWORD)
class SetPasswordActivity : BaseActivity<SetPasswordPresenter>(), SetPasswordContract.View {

    override fun registerCallback(response: BaseResponse<String>) {
        if (!response.isSuccess()) {
            toast(response.statusMsg)
        } else {
            Application.user = User(intent.getStringExtra(RegisterActivity.PARAM_PHONE_NUMBER))
            jump(RouterPath.UserCenter.SET_HOUSE_INFO)
            finish()
        }
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_set_password).withToolbar("", true, unLine = false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        et_password.onFocusChange { _, hasFocus ->
            if (hasFocus) {
                txl_password.hint = "密码长度至少8个字符"
            } else {
                txl_password.hint = "设置密码"
            }
        }

        floating_action_btn.onClick {
            presenter {
                if (chekcPassword()) {
                    register(intent.getStringExtra(RegisterActivity.PARAM_PHONE_NUMBER), et_password.trimValue(), intent.getStringExtra(RegisterActivity.PARAM_CODE))
                }
            }
        }
    }


    private fun chekcPassword(): Boolean {
        if (et_password.trimValue().isNullOrEmpty()) {
            toast("请输入密码")
            return false
        }
        if (et_password.trimValue().length < 8) {
            toast("密码长度至少8个字符")
            return false
        }
        return true
    }

}

package com.ysjkj.smart.community.user.mvp.ui.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import com.alibaba.android.arouter.facade.annotation.Route
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.provider.router.RouterPath
import com.ysjkj.smart.community.user.R
import kotlinx.android.synthetic.main.user_activity_set_house_info.*

@Route(path = RouterPath.UserCenter.SET_HOUSE_INFO)
class SetHouseInfoActivity : BaseActivity<EmptyPresenter>() {

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.user_activity_set_house_info).withToolbar("", true,unLine = false)
    }

    override fun initView(savedInstanceState: Bundle?) {
        sp_communit.apply {
            adapter = ArrayAdapter<String>(this@SetHouseInfoActivity, R.layout.user_spinner_item_simple, R.id.tx_title, mutableListOf("小区1", "小区2", "小区3"))
        }

        sp_user_info.apply {
            adapter = ArrayAdapter<String>(this@SetHouseInfoActivity, R.layout.user_spinner_item_simple, R.id.tx_title, mutableListOf("身份证", "军官证", "暂住证"))
        }
    }

}

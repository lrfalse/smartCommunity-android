package com.ysjkj.smart.community.mvp.ui

import android.os.Bundle
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.base.ViewBuilder
import com.piaolac.core.di.component.ActivityComponent
import com.piaolac.core.mvp.EmptyPresenter
import com.ysjkj.smart.community.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<EmptyPresenter>() {
    override fun setUpComponet(activityComponent: ActivityComponent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initViewConfig(): ViewBuilder.() -> Unit = {
        withContent(R.layout.activity_main).withToolbar("测试而已", true).withState().withRefresh()
    }

    override fun initView(savedInstanceState: Bundle?) {

        button2.setOnClickListener {
        }

    }
}

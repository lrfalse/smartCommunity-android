package com.piaolac.core.base

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.alibaba.android.arouter.launcher.ARouter
import com.piaolac.core.ext.translucentStatus
import com.piaolac.core.mvp.BasePresenter
import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.piaolac.core.utils.ReflectionUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by yangqiang on 2018/3/14.
 */
@Suppress("LeakingThis")
abstract class BaseActivity<out P : BasePresenter<IModel, IView>> : RxAppCompatActivity(), Toolbar.OnMenuItemClickListener, IViewConfig {
    lateinit var builder: ViewBuilder
    private var presenter: P = ReflectionUtils.getSuperClassGenricType(this, 0)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        translucentStatus(this)
        setContentView(ViewBuilder(this).apply {
            builder = this
        }.apply(initViewConfig()).createContent())
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
    }

    abstract fun initView(savedInstanceState: Bundle?)
    override fun onMenuItemClick(item: MenuItem?) = true

    fun presenter(block: P.() -> Unit) {
        presenter.apply(block)
    }

    fun presenter(): P {
        return presenter
    }

}


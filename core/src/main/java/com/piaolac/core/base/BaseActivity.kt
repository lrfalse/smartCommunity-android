package com.piaolac.core.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.alibaba.android.arouter.launcher.ARouter
import com.piaolac.core.mvp.BasePresenter
import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.piaolac.core.utils.ReflectionUtils
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import org.jetbrains.anko.toast

/**
 * Created by yangqiang on 2018/3/14.
 */
@Suppress("LeakingThis")
abstract class BaseActivity<out P : BasePresenter<IModel, IView>> : RxAppCompatActivity(), Toolbar.OnMenuItemClickListener, IViewConfig {
    lateinit var builder: ViewBuilder
    private var presenter: P = ReflectionUtils.getSuperClassGenricType(this, 0)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        presenter?.inject(this)
//        translucentStatus(this)
        setContentView(ViewBuilder(this).apply {
            builder = this
        }.apply(initViewConfig()).createContent())
        ARouter.getInstance().inject(this)
        initView(savedInstanceState)
        AppManager.addActivity(this)
    }

    abstract fun initView(savedInstanceState: Bundle?)
    override fun onMenuItemClick(item: MenuItem?) = true

    fun presenter(block: P.() -> Unit) {
        presenter.apply(block)
    }

    fun presenter(): P {
        return presenter
    }

    fun replaceFragment(contentId: Int, fragment: Fragment, tag: String = fragment.javaClass.name) {
        if (supportFragmentManager.fragments.contains(fragment)) {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().replace(contentId, fragment, tag).commit()
        }
    }

    fun addFragment(contentId: Int, fragment: Fragment, tag: String = fragment.javaClass.name) {
        if (supportFragmentManager.fragments.contains(fragment)) {
            supportFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            supportFragmentManager.beginTransaction().add(contentId, fragment, tag).commit()
        }
    }

    fun removeFragment(fragment: Fragment) {
        if (!supportFragmentManager.fragments.contains(fragment)) {
            return
        }
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppManager.finishActivity(this)
    }

    var lastTime = 0L

    override fun onBackPressed() {
        System.currentTimeMillis().apply {
            if (this - lastTime > 2 * 1000 && isTaskRoot) {
                toast("再次点击退出应用")
                lastTime = this
            } else {
                super.onBackPressed()
            }
        }
    }

}


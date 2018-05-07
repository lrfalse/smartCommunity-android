package com.piaolac.core.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.launcher.ARouter
import com.piaolac.core.mvp.BasePresenter
import com.piaolac.core.utils.ReflectionUtils
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by yangqiang on 2018/3/16.
 */
@Suppress("LeakingThis")
abstract class BaseFragment<out P : BasePresenter<*, *>> : RxFragment(), Toolbar.OnMenuItemClickListener, IViewConfig {

    lateinit var builder: ViewBuilder
    private var presenter: P = ReflectionUtils.getSuperClassGenricType(this, 0)!!
    private var isFragmentVisible: Boolean = false
    private var isPrepared: Boolean = false
    private var isFirstLoad = true
    private var isLazyLoad: Boolean = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        presenter?.inject(this)
        isFirstLoad = true
        isPrepared = true
        return ViewBuilder(activity as FragmentActivity).apply {
            builder = this
            isLazyLoad = lazyLoad
        }.apply(initViewConfig()).createContent()
        ARouter.getInstance().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (isLazyLoad) {
            lazyLoad()
        } else {
            initView(view, savedInstanceState)
        }
    }




    abstract fun initView(view: View?, savedInstanceState: Bundle?)
    override fun onMenuItemClick(item: MenuItem?) = true

    fun presenter(block: P.() -> Unit) {
        presenter.apply(block)
    }

    fun presenter(): P {
        return presenter
    }

    protected fun lazyLoad() {
        if (isPrepared && isFragmentVisible) {
            if (isFirstLoad) {
                isFirstLoad = false
                if (isLazyLoad) {
                    initView(view, arguments)
                }
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        if (!hidden) {
            isFragmentVisible = true
            lazyLoad()
        } else {
            isFragmentVisible = false
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isFragmentVisible = true
            lazyLoad()
        } else {
            isFragmentVisible = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isPrepared = false
    }


    fun replaceFragment(contentId: Int, fragment: Fragment, tag: String = fragment.javaClass.name) {
        if (childFragmentManager.fragments.contains(fragment)) {
            childFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            childFragmentManager.beginTransaction().replace(contentId, fragment, tag).commit()
        }
    }

    fun addFragment(contentId: Int, fragment: Fragment, tag: String = fragment.javaClass.name) {
        if (childFragmentManager.fragments.contains(fragment)) {
            childFragmentManager.beginTransaction().show(fragment).commit()
        } else {
            childFragmentManager.beginTransaction().add(contentId, fragment, tag).commit()
        }
    }

    fun removeFragment(fragment: Fragment) {
        if (!childFragmentManager.fragments.contains(fragment)) {
            return
        }
        childFragmentManager.beginTransaction().remove(fragment).commit()
    }


}
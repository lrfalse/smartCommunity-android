package com.piaolac.core.transformer

import android.accounts.NetworkErrorException
import com.google.gson.JsonSyntaxException
import com.piaolac.brick.common.view.MultiStateView
import com.piaolac.core.base.BaseApplication
import com.piaolac.core.base.IEmptyState
import com.piaolac.core.base.refreshView
import com.piaolac.core.base.stateView
import com.piaolac.core.ext.ifNotNull
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.trello.rxlifecycle2.LifecycleProvider
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import java.net.ConnectException

/**
 * Created by yangqiang on 2018/3/19.
 */
class BindViewTransformer<T>(var lifecycleProvider: LifecycleProvider<*>?, var showError: Boolean = false) : ObservableTransformer<T, T> {
    override fun apply(it: Observable<T>): ObservableSource<T> {
        return it.observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    lifecycleProvider?.apply {
                        refreshView().ifNotNull<SmartRefreshLayout>({
                            if (!isRefreshing) {
                                stateView()?.apply {
                                    setState(MultiStateView.LOADING)
                                }
                            }
                        }, {
                            stateView()?.apply {
                                setState(MultiStateView.LOADING)
                            }
                        })
                    }
                }
                .doAfterNext {
                    lifecycleProvider?.apply {
                        refreshView()?.apply {
                            finishRefresh()
                            finishLoadMore()
                        }
                        (it as? IEmptyState).ifNotNull<IEmptyState>({
                            stateView()?.apply {
                                if (showEmpty()) {
                                    setState(MultiStateView.EMPTY)
                                } else {
                                    setState(MultiStateView.CONTENT)
                                }
                            }
                        }, {
                            stateView()?.apply {
                                setState(MultiStateView.CONTENT)
                            }
                        })
                    }
                }
                .doOnError {
                    when (it) {
                        is JsonSyntaxException -> BaseApplication.context.longToast("服务器返回数据解析错误")
                        is NetworkErrorException -> BaseApplication.context.longToast("网络异常请稍后再试")
                        is ConnectException -> BaseApplication.context.longToast("网络异常请稍后再试")
                        else -> BaseApplication.context.toast("系统异常")
                    }

                    lifecycleProvider?.apply {
                        refreshView()?.apply {
                            finishRefresh()
                            finishLoadMore()
                        }
                        stateView()?.apply {
                            if (showError) {
                                setState(MultiStateView.ERROR)
                            } else {
                                setState(MultiStateView.CONTENT)
                            }
                        }
                    }
                }
    }
}
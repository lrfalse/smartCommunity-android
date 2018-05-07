package com.piaolac.core.transformer

import android.app.Activity
import android.support.v4.app.Fragment
import com.piaolac.core.utils.RxUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by yangqiang on 2018/3/19.
 */
class LifecycleTransformer<T>(
        private var event: Any = ActivityEvent.DESTROY,
        private var lifecycleProvider: LifecycleProvider<*>) : ObservableTransformer<T, T> {
    override fun apply(it: Observable<T>): ObservableSource<T> {
        return it
                .compose(RxUtils.androidTransformer()).let {
                    return if ((lifecycleProvider is Activity && event !is ActivityEvent) || (lifecycleProvider is Fragment && event !is FragmentEvent)) {
                        it.bindToLifecycle(lifecycleProvider)
                    } else {
                        it.bindUntilEvent(lifecycleProvider as LifecycleProvider<Any>, event)
                    }
                }

    }
}
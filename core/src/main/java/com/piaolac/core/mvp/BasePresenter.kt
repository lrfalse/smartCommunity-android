package com.piaolac.core.mvp

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import com.piaolac.core.utils.ReflectionUtils
import com.trello.rxlifecycle2.LifecycleProvider

/**
 * Created by YangQiang on 2017/11/6.
 */
abstract class BasePresenter<out M : IModel, out V : IView> {
    private var model: M? = ReflectionUtils.getSuperClassGenricType<M>(this, 0)
    private var view: V? = null
    var context: Context? = null
    open var lifecycleProvider: LifecycleProvider<Any>? = null

    fun inject(any: Any) {
        (any as?Activity)?.let {
            context = it
        }
        (any as?Fragment)?.let {
            context = it.context
        }
        view = any as?V
        lifecycleProvider = any as?LifecycleProvider<Any>
    }

    fun model(block: M.() -> Unit) {
        model?.apply(block)
    }

    fun model() = model
    fun view() = view

    fun view(block: V.() -> Unit) {
        view?.apply(block)
    }

//    fun <T : Any> Observable<T>.execute(transformer: ObservableTransformer<T, T> = BindViewTransformer(lifecycleProvider),
//                                        delayMilliSeconds: Long = 500L,
//                                        event: Any = ActivityEvent.DESTROY,
//                                        baseObserver: BaseObserver<T>.() -> Unit) {
//        if (lifecycleProvider == null) {
//            this.delay(delayMilliSeconds, TimeUnit.MILLISECONDS)
//                    .compose(transformer)
//                    .subscribe(BaseObserver<T>().apply(baseObserver))
//        } else {
//            this.delay(delayMilliSeconds, TimeUnit.MILLISECONDS)
//                    .compose(LifecycleTransformer(event, lifecycleProvider!!))
//                    .compose(transformer).subscribe(BaseObserver<T>().apply(baseObserver).apply {
//                    })
//
//        }
//
//    }

}

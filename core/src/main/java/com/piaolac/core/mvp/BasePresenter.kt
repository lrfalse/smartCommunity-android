package com.piaolac.core.mvp

import android.content.Context
import com.piaolac.core.base.BaseObserver
import com.piaolac.core.transformer.BindViewTransformer
import com.piaolac.core.transformer.LifecycleTransformer
import com.piaolac.core.utils.ReflectionUtils
import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableTransformer

/**
 * Created by YangQiang on 2017/11/6.
 */
abstract class BasePresenter<out M : IModel, out V : IView> {
    private var model: M? = ReflectionUtils.getSuperClassGenricType<M>(this, 0)
    private var view: V? = null
    private var context: Context? = null
    var lifecycleProvider: LifecycleProvider<Any>? = null

    fun context(context: Context) {
        this.context = context
        (context as?V).apply { view = this }
        (context as?LifecycleProvider<Any>).apply { lifecycleProvider = this }
    }

    fun model(block: M.() -> Unit) {
        model?.apply(block)
    }

    fun model() = model
    fun view() = view

    fun view(block: V.() -> Unit) {
        view?.apply(block)
    }

    fun <T : Any> Observable<T>.execute(transformer: ObservableTransformer<T, T> = BindViewTransformer(lifecycleProvider),
                                        delayMilliSeconds: Long = 500L,
                                        event: Any = ActivityEvent.DESTROY,
                                        baseObserver: BaseObserver<T>.() -> Unit) {
        if (lifecycleProvider == null) {
            this.compose(transformer).subscribe(BaseObserver<T>().apply(baseObserver))
        } else {
            this.compose(LifecycleTransformer(delayMilliSeconds, event, lifecycleProvider!!))
                    .compose(transformer).subscribe(BaseObserver<T>().apply(baseObserver))
        }

    }

}

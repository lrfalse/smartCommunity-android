package com.ysjkj.smart.community.provider.base

import com.piaolac.core.base.BaseObserver
import com.piaolac.core.mvp.BasePresenter
import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.piaolac.core.transformer.BindViewTransformer
import com.piaolac.core.transformer.LifecycleTransformer
import com.trello.rxlifecycle2.android.ActivityEvent
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import org.jetbrains.anko.toast
import java.util.concurrent.TimeUnit

/**
 *
 * @author yangqiang
 * @create 2018-05-04 上午10:23
 **/
open class ProviderPresenter<out M : IModel, out V : IView> : BasePresenter<M, V>() {

    fun <T : Any> Observable<T>.execute(transformer: ObservableTransformer<T, T> = BindViewTransformer(lifecycleProvider),
                                        delayMilliSeconds: Long = 500L,
                                        event: Any = ActivityEvent.DESTROY,
                                        showFailedMsg: Boolean = true,
                                        showErrorMsg: Boolean = true,
                                        baseObserver: BaseObserver<T>.() -> Unit) {
        if (lifecycleProvider == null) {
            this.delay(delayMilliSeconds, TimeUnit.MILLISECONDS)
                    .compose(transformer)
                    .subscribe(BaseObserver<T>().apply(baseObserver))
        } else {
            this.delay(delayMilliSeconds, TimeUnit.MILLISECONDS)
                    .compose(LifecycleTransformer(event, lifecycleProvider!!))
                    .compose(transformer)
                    .doAfterNext {
                        (it as? BaseResponse<*>)?.apply {
                            if (!isSuccess() && showFailedMsg) {
                                statusMsg?.apply {
                                    context?.toast(this)
                                }
                            }
                        }
                    }.doOnError {
                        if (showErrorMsg) {
                            context?.toast(it.localizedMessage)
                        }
                    }.subscribe(BaseObserver<T>().apply(baseObserver))

        }

    }

}
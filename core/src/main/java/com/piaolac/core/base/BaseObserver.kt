package com.piaolac.core.base

import io.reactivex.observers.DefaultObserver

/**
 * Created by YangQiang on 2017/11/6.
 */
open class BaseObserver<T> : DefaultObserver<T>() {

    private var onNext: (T) -> Unit = {}
    private var onError: (Throwable) -> Unit = {}
    private var onComplate: () -> Unit = {}
    override fun onNext(t: T) {
        onNext.invoke(t)
    }

    fun onNext(block: (T) -> Unit) {
        onNext = block
    }


    override fun onError(e: Throwable) {
        onError.invoke(e)
    }

    fun onError(block: (Throwable) -> Unit) {
        onError = block
    }

    override fun onComplete() {
        onComplate.invoke()
    }

    fun onComplate(block: () -> Unit) {
        onComplate = block
    }
}
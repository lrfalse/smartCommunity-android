package com.ysjkj.smart.community.provider.transformer

import android.content.Context
import android.support.v7.app.AlertDialog
import com.ysjkj.smart.community.provider.ext.loadingDialog
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject

/**
 * Created by yangqiang on 2018/3/20.
 */
class LoadingTransformer<T>(private var ctx: Context?) : ObservableTransformer<T, T> {

    var alertDialog: AlertDialog? = null
    var subject = PublishSubject.create<Boolean>()
    override fun apply(it: Observable<T>): ObservableSource<T> {
        return it.takeUntil(subject).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    alertDialog = ctx?.loadingDialog(true, false) {
                        subject.onNext(true)
                    }
                }.doOnComplete {
                    alertDialog?.dismiss()
                }.doOnError {
                    alertDialog?.dismiss()
                }
    }

}
package com.piaolac.core.transformer

import android.app.ProgressDialog
import android.content.Context
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer

/**
 * Created by yangqiang on 2018/3/20.
 */
class ProgressTransformer<T>(private var ctx: Context) : ObservableTransformer<T, T> {
    init {
        val progressDialog: ProgressDialog by lazy { ProgressDialog(ctx) }
    }

    override fun apply(it: Observable<T>): ObservableSource<T> {

        return it
    }

}
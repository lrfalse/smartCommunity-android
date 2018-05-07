package com.ysjkj.smart.community.life.mvp.contract

import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 *
 * @author yangqiang
 * @create 2018-04-24 下午9:14
 **/
interface GroupShoppingContract {
    interface View : IView {
        fun onTimeTick(count: Long)
    }

    class Model : IModel {
        fun countdownTimer(): Observable<Long> {
            return Observable.interval(0, 1, TimeUnit.SECONDS)
        }
    }
}
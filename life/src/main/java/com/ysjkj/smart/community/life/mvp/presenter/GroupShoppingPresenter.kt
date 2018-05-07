package com.ysjkj.smart.community.life.mvp.presenter

import com.ysjkj.smart.community.life.mvp.contract.GroupShoppingContract
import com.ysjkj.smart.community.provider.base.ProviderPresenter

/**
 * 团购
 * @author yangqiang
 * @create 2018-04-24 下午9:14
 **/
class GroupShoppingPresenter : ProviderPresenter<GroupShoppingContract.Model, GroupShoppingContract.View>() {

    fun countdownTimer() {
        model {
            countdownTimer().execute {
                onNext {
                    view {
                        onTimeTick(it)
                    }
                }
            }
        }
    }
}
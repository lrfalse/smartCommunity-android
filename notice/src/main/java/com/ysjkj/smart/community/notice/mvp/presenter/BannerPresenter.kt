package com.ysjkj.smart.community.notice.mvp.presenter

import com.ysjkj.smart.community.notice.mvp.contract.BannerContract
import com.ysjkj.smart.community.provider.base.ProviderPresenter

/**
 * 广告
 * @author yangqiang
 * @create 2018-05-04 下午3:30
 **/
class BannerPresenter : ProviderPresenter<BannerContract.Model, BannerContract.View>() {

    /**
     * 广告列表
     */
    fun noticeList(id: String) {
        model {
            noticeList(id).execute {
                onNext {
                    if(it.isSuccess()){
                        view {
                            onNoticeResult(it.body)
                        }
                    }
                }
            }
        }
    }
}
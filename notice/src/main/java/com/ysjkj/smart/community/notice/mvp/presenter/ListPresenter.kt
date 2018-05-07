package com.ysjkj.smart.community.notice.mvp.presenter

import com.ysjkj.smart.community.notice.mvp.contract.ListContract
import com.ysjkj.smart.community.provider.base.PageRequest
import com.ysjkj.smart.community.provider.base.ProviderPresenter

/**
 *
 * @author yangqiang
 * @create 2018-05-04 下午4:12
 **/
class ListPresenter : ProviderPresenter<ListContract.Model, ListContract.View>() {

    /**
     * 公告列表
     */
    fun noticeList(p: Int, id: String, r: Int = PageRequest.ROWS) {
        model {
            noticeList(p, r, id).execute {
                onNext {
                    if (it.isSuccess()) {

                    }
                }
            }
        }
    }
}
package com.ysjkj.smart.community.notice.mvp.contract

import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.ysjkj.smart.community.notice.api.NoticeApi
import com.ysjkj.smart.community.notice.mvp.model.ResponseMarqueeNotice
import com.ysjkj.smart.community.provider.ApiFactory
import com.ysjkj.smart.community.provider.base.BaseResponse
import io.reactivex.Observable

/**
 *
 * @author yangqiang
 * @create 2018-05-04 下午3:30
 **/
interface BannerContract {
    interface View : IView {
        fun onNoticeResult(result: ResponseMarqueeNotice)
    }

    class Model : IModel {

        /**
         * 广告列表
         */
        fun noticeList(id: String): Observable<BaseResponse<ResponseMarqueeNotice>> {
            return ApiFactory.createApi<NoticeApi>(NoticeApi.URL, NoticeApi.PORT).marqueeNoticeList(id)
        }
    }
}
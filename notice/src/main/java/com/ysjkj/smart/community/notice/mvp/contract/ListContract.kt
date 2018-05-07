package com.ysjkj.smart.community.notice.mvp.contract

import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.ysjkj.smart.community.notice.api.NoticeApi
import com.ysjkj.smart.community.provider.ApiFactory
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.base.PageRequest
import io.reactivex.Observable

/**
 *
 * @author yangqiang
 * @create 2018-05-04 下午4:12
 **/
interface ListContract {

    interface View : IView{
        fun onNoticeListResult()
    }

    class Model : IModel {
        fun noticeList(p: Int, r: Int, id: String): Observable<BaseResponse<String>> {
            return ApiFactory.createApi<NoticeApi>(NoticeApi.URL, NoticeApi.PORT).noticeList(PageRequest().apply {
                this.page = p
                this.rows = r
                put("communityId", id)
            })
        }
    }
}


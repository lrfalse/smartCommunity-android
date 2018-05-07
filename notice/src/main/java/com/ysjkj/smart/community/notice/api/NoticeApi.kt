package com.ysjkj.smart.community.notice.api

import com.ysjkj.smart.community.notice.mvp.model.ResponseMarqueeNotice
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.base.PageRequest
import io.reactivex.Observable
import retrofit2.http.Field
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 公告api
 * @author yangqiang
 * @create 2018-05-04 下午3:27
 **/
interface NoticeApi {
    companion object {
        const val URL = "http://192.168.1.20"
        const val PORT = 8080
    }

    @POST("getNotice")
    @FormUrlEncoded
    fun marqueeNoticeList(@Field("communityId") id: String): Observable<BaseResponse<ResponseMarqueeNotice>>


    @POST("getNoticeList")
    @FormUrlEncoded
    fun noticeList(@FieldMap map: PageRequest): Observable<BaseResponse<String>>
}
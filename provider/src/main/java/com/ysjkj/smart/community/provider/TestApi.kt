package com.ysjkj.smart.community.provider

import com.ysjkj.smart.community.provider.base.BaseResponse
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * test
 * @author
 * @create 2018-04-04 下午12:57
 **/
interface TestApi {
    @POST("test")
    @FormUrlEncoded
    fun test(@FieldMap map: Map<String, String>): Observable<BaseResponse>
}
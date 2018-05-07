package com.ysjkj.smart.community.user.api

import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.model.User
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * 用户中心接口
 * @author
 * @create 2018-04-04 下午12:57
 **/
interface UserCenterApi {
    companion object {
        const val URL = "http://192.168.1.20"
        const val PORT = 8084
    }

    /**
     * 用户注册
     */
    @POST("register")
    @FormUrlEncoded
    fun register(@FieldMap map: Map<String, String>): Observable<BaseResponse<String>>


    /**
     * 检查验证码
     */
    @POST("checkCode")
    @FormUrlEncoded
    fun checkCode(@FieldMap map: Map<String, String>): Observable<BaseResponse<String>>

    /**
     * 登录接口
     */
    @POST("login")
    @FormUrlEncoded
    fun login(@FieldMap map: Map<String, String>): Observable<BaseResponse<User>>
}
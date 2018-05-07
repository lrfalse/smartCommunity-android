package com.ysjkj.smart.community.provider.base

import java.io.Serializable

/**
 * 请求返回的结构体
 * @author
 * @create 2018-04-04 下午12:56
 **/

open class BaseResponse<T>(var statusCode: String = "", var statusMsg: String = "", var body: T) : Serializable {
    fun isSuccess(): Boolean {
        return when (statusCode) {
            "000" -> {
                true
            }
            "100" -> {
                false
            }
            "500" -> {
                false
            }
            else -> {
                false
            }
        }
    }
}
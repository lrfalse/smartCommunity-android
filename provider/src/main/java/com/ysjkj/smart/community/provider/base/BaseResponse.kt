package com.ysjkj.smart.community.provider.base

/**
 * 请求返回的结构体
 * @author
 * @create 2018-04-04 下午12:56
 **/
data class BaseResponse(var key: String, var statusCode: String, var statusMsg: String, var body: String)
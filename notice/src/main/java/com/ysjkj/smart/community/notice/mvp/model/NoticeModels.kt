package com.ysjkj.smart.community.notice.mvp.model

import com.google.gson.annotations.SerializedName


/**
 *
 * @author yangqiang
 * @create 2018-05-04 下午3:58
 **/

/**
 * 上下滚动的社区公告列表
 */
data class ResponseMarqueeNotice(
        @SerializedName("total") val total: Int, //1
        @SerializedName("currentPage") val currentPage: Int, //1
        @SerializedName("list") val list: List<String>
)
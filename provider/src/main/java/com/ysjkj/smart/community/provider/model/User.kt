package com.ysjkj.smart.community.provider.model

import com.google.gson.annotations.SerializedName


/**
 * 用户信息
 * @author yangqiang
 * @create 2018-04-27 下午10:20
 **/

data class User(
        @SerializedName("mobphone") var mobphone: String = "",
        @SerializedName("name") var name: String = "", //杨强
        @SerializedName("sex") var sex: String = "", //男
        @SerializedName("image_url") var imageUrl: String = "", //http://thirdwx.qlogo.cn/mmopen/vi_32/TiciaBFlLNOEOJyNFMhDibdsv77FJcTzqjtFia5I1yaISiaQYQkDyJXlHWUcYU2YSGjwOLIa1DKfEcUgSHyFPKDyzIg/132
        @SerializedName("token") var token: String = "1", //1
        @SerializedName("status") var status: String = ""
) {

    /**
     * 是否首次登陆
     */
    fun isFirstLogin() = token == "1"

}
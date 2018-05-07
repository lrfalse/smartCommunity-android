package com.ysjkj.smart.community.user.mvp.contract

import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.ysjkj.smart.community.provider.ApiFactory
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.user.api.UserCenterApi
import io.reactivex.Observable

/**
 *
 * @author yangqiang
 * @create 2018-04-27 上午11:39
 **/
interface SetPasswordContract {
    interface View : IView {
        fun registerCallback(response: BaseResponse<String>)
    }

    class Model : IModel {
        /**
         * 注册账号
         */
        fun register(phone: String, pwd: String, code: String): Observable<BaseResponse<String>> {

            return ApiFactory.createApi<UserCenterApi>(UserCenterApi.URL, UserCenterApi.PORT)
                    .register(mapOf("mobPhone" to phone, "pwd" to pwd, "authCode" to code))
        }
    }
}
package com.ysjkj.smart.community.user.mvp.contract

import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.ysjkj.smart.community.provider.ApiFactory
import com.ysjkj.smart.community.provider.base.BaseResponse
import com.ysjkj.smart.community.provider.model.User
import com.ysjkj.smart.community.user.api.UserCenterApi
import io.reactivex.Observable

/**
 * Created by yangqiang on 2018/3/29.
 */
interface LoginContract {
    interface View : IView{
        fun loginResult(result:BaseResponse<User>)
    }

    class Model : IModel {
        fun login(map: Map<String, String>): Observable<BaseResponse<User>> {
            return ApiFactory.createApi<UserCenterApi>(UserCenterApi.URL, UserCenterApi.PORT).login(map)
        }
    }
}
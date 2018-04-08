package com.ysjkj.smart.community.umeng

import android.content.Context
import com.umeng.commonsdk.UMConfigure
import com.umeng.socialize.PlatformConfig
import com.umeng.socialize.UMShareAPI

/**
 * Created by yangqiang on 2018/4/2.
 */
object ConfigUtils {

    /**
     * 初始化友盟配置
     */
    fun init(context: Context) {
        UMConfigure.init(context, "5ac1dd1d8f4a9d34dd0000b6", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "495e03faee0a3e0f84498b3ccd09b61d")

        //qq
        PlatformConfig.setQQZone("101464903", "ec9645b9000105f8f6f7d6557bc52cb7")

        //微信
        PlatformConfig.setWeixin("wxdd72a0171dbec579", "d32773b93b3a1f351df1f53bafc70eff")



        UMShareAPI.get(context)
    }
}
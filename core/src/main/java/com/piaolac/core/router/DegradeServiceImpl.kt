package com.piaolac.core.router

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.piaolac.core.R
import com.piaolac.core.base.BaseApplication
import org.jetbrains.anko.toast

/**
 * Created by yangqiang on 2018/3/28.
 */
@Route(path = "/xxx/xxx")
class DegradeServiceImpl : DegradeService {
    override fun onLost(context: Context?, postcard: Postcard) {
        BaseApplication.context.toast(BaseApplication.context.getString(R.string.core_router_error))
    }

    override fun init(context: Context) {

    }
}
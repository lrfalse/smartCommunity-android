package com.ysjkj.smart.community.provider.base

import android.content.Intent
import com.piaolac.core.base.BaseActivity
import com.piaolac.core.mvp.BasePresenter
import com.piaolac.core.mvp.IModel
import com.piaolac.core.mvp.IView
import com.umeng.socialize.UMShareAPI

/**
 * Created by yangqiang on 2018/3/14.
 */
@Suppress("LeakingThis")
abstract class BaseUMengActivity<out P : BasePresenter<IModel, IView>> : BaseActivity<P>(){

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        UMShareAPI.get(this).release()
    }
}


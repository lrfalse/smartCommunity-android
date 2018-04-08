package com.ysjkj.smart.community.umeng

import android.app.Activity
import com.piaolac.core.utils.RxUtils
import com.umeng.socialize.ShareAction
import com.umeng.socialize.UMAuthListener
import com.umeng.socialize.UMShareAPI
import com.umeng.socialize.UMShareListener
import com.umeng.socialize.bean.SHARE_MEDIA
import io.reactivex.Observable

/**
 * Created by yangqiang on 2018/4/2.
 */

enum class UMengCode {
    SUCCESS, CANCEL, ERROR
}

data class UMengData(var platform: SHARE_MEDIA, var data: MutableMap<String, String>, var code: UMengCode, var error: Throwable? = null)


/**
 * 第三方登录
 */
fun Activity.authLogin(platform: SHARE_MEDIA): Observable<UMengData> {
    return Observable.create<UMengData> {
        UMShareAPI.get(this).getPlatformInfo(this, platform, object : UMAuthListener {

            override fun onComplete(p0: SHARE_MEDIA, p1: Int, p2: MutableMap<String, String>) {
                it.onNext(UMengData(p0, p2, UMengCode.SUCCESS))
                it.onComplete()
            }

            override fun onCancel(p0: SHARE_MEDIA, p1: Int) {
                it.onNext(UMengData(p0, mutableMapOf(), UMengCode.CANCEL))
                it.onComplete()
            }

            override fun onError(p0: SHARE_MEDIA, p1: Int, p2: Throwable?) {
                it.onNext(UMengData(p0, mutableMapOf(), UMengCode.ERROR, p2))
                it.onComplete()
            }

            override fun onStart(p0: SHARE_MEDIA?) {
            }

        })
    }.compose(RxUtils.androidTransformer())
}

/**
 * 第三方分享
 */
fun Activity.share(shareAction: ShareAction): Observable<UMengData> {
    return Observable.create {
        shareAction.setCallback(object : UMShareListener {
            override fun onResult(p0: SHARE_MEDIA) {
                it.onNext(UMengData(p0, mutableMapOf(), UMengCode.SUCCESS))
                it.onComplete()
            }

            override fun onCancel(p0: SHARE_MEDIA) {
                it.onNext(UMengData(p0, mutableMapOf(), UMengCode.CANCEL))
                it.onComplete()
            }

            override fun onError(p0: SHARE_MEDIA, p1: Throwable?) {
                it.onNext(UMengData(p0, mutableMapOf(), UMengCode.ERROR, p1))
                it.onComplete()
            }

            override fun onStart(p0: SHARE_MEDIA?) {
            }

        }).share()
    }

}

package com.ysjkj.smart.community.provider

import com.google.gson.Gson
import com.piaolac.core.net.NetManager
import com.piaolac.core.utils.Utils
import com.ysjkj.smart.community.provider.utils.AESEncryptUtils
import okhttp3.FormBody
import okhttp3.Interceptor

object ApiFactory {
    var url = "http://192.168.1.10:8080/"
    var apiInterface = mutableMapOf<String, Any>()
    private var gson = Gson()
    var retrofit = NetManager.Builder(url, interceptors = createInterceptors()).build()

    /**
     * 创建api接口
     */
    inline fun <reified T> createApi(url: String = ApiFactory.url): T {
        return if (apiInterface.containsKey(T::class.java.name)) {
            apiInterface[T::class.java.name] as T
        } else {
            if (ApiFactory.url == url) {
                retrofit.create(T::class.java).apply {
                    apiInterface[T::class.java.name] = this as Any
                }
            } else {
                retrofit.newBuilder().baseUrl(url).build().create(T::class.java).apply {
                    apiInterface[T::class.java.name] = this as Any
                }
            }
        }
    }

    /**
     * 创建拦截器
     */
    private fun createInterceptors(): MutableList<Interceptor> {
        return mutableListOf<Interceptor>().apply {
            add(Interceptor {
                var req = it.request()
                var body = FormBody.Builder()
                var newReq = req.newBuilder()
                body.add("tag", "A")
                (req.body()as? FormBody)?.let {
                    mutableMapOf<String, String>().let { map ->
                        (0 until it.size()).forEachIndexed { index, i ->
                            map[it.name(index)] = it.value(index)
                        }
                        gson.toJson(map).let { gs ->
                            AESEncryptUtils.encrypt(gs)?.apply {
                                body.add("body", this)
                            }
                            body.add("key", Utils.md5(gs))
                        }
                    }
                }
                newReq.post(body.build())
                it.proceed(newReq.build())
            })
        }
    }
}
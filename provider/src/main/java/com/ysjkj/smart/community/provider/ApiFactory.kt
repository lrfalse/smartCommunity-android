package com.ysjkj.smart.community.provider

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.piaolac.core.net.NetManager
import com.piaolac.core.utils.Utils
import com.ysjkj.smart.community.provider.utils.AESEncryptUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import java.nio.charset.Charset

object ApiFactory {
    var url = "http://192.168.1.10"
    var apiInterface = mutableMapOf<String, Any>()
    private var gson = Gson()
    var retrofit = NetManager.Builder(url, interceptors = createInterceptors()).build()

    /**
     * 创建api接口
     */
    inline fun <reified T> createApi(url: String = ApiFactory.url, port: Int = 8080): T {
        return if (apiInterface.containsKey(T::class.java.name)) {
            apiInterface[T::class.java.name] as T
        } else {
            retrofit.newBuilder().baseUrl("$url:$port/").build().create(T::class.java).apply {
                apiInterface[T::class.java.name] = this as Any
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
                            LogUtils.d(gs.toString())
                            AESEncryptUtils.encrypt(gs)?.apply {
                                body.add("body", this)
                            }
                            body.add("key", Utils.md5(gs))
                        }
                    }
                }

                it.proceed(newReq.post(body.build()).build()).let {
                    if (it.isSuccessful) {
                        var responseBody: ResponseBody? = null

                        //重写返回数据结构
                        it.body()?.apply {
                            source().apply {
                                request(Long.MAX_VALUE)
                                buffer().clone().readString(Charset.forName("UTF-8")).apply {
                                    JSONObject(this).let {
                                        if (it.has("body")) {
                                            var desBody = AESEncryptUtils.decrypt(it.getString("body"))
                                            if (!desBody.isNullOrEmpty()) {
                                                try {
                                                    JSONObject(desBody).apply {
                                                        it.put("body", this)
                                                    }
                                                } catch (e: Exception) {
                                                }

                                                try {
                                                    JSONArray(desBody).apply {
                                                        it.put("body", this)
                                                    }
                                                } catch (e: Exception) {
                                                }
                                            }
                                        }
                                        it

                                    }.apply {
                                        LogUtils.d(this)
                                        responseBody = ResponseBody.create(null, this.toString())
                                    }
                                }
                            }
                        }
                        it.newBuilder().body(responseBody).build()
                    } else {
                        it
                    }
                }
            })
        }
    }
}
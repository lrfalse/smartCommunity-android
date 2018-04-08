package com.piaolac.core.net

import com.piaolac.core.base.BaseApplication
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 网络请求
 * @author
 * @create 2018-04-04 上午10:53
 **/
object NetManager {

    class Builder(val url: String = "",
                  var timeOut: Long = 60,
                  var cacheFile: File? = null,
                  var logLevel: HttpLoggingInterceptor.Level = HttpLoggingInterceptor.Level.BODY,
                  var interceptors: MutableList<Interceptor> = mutableListOf()) {


        fun build(): Retrofit {
            var builder = OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .connectTimeout(timeOut, TimeUnit.SECONDS)
                    .writeTimeout(timeOut, TimeUnit.SECONDS)
                    .readTimeout(timeOut, TimeUnit.SECONDS)
                    .connectionPool(ConnectionPool(50, 60, TimeUnit.SECONDS))
                    .addNetworkInterceptor(HttpLoggingInterceptor().apply { level = logLevel })
                    .let {
                        cacheFile?.apply {
                            //20M缓存
                            it.cache(Cache(BaseApplication.context.cacheDir, 20 * 1024 * 1024))
                        }
                        interceptors.forEach { tor ->
                            it.addInterceptor(tor)
                        }
                        it
                    }.addNetworkInterceptor {
                        it.proceed(it.request()).let {
                            it.newBuilder().removeHeader("Pragma").header("Cache-Control", it.request().cacheControl().toString()).build()
                        }
                    }

            return Retrofit.Builder()
                    .baseUrl(url)
                    .client(builder.build())
                    .addCallAdapterFactory(
                            RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
    }

}
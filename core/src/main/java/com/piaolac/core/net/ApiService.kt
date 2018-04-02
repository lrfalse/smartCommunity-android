package com.piaolac.core.net

import com.piaolac.core.base.BaseApplication
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by yangqiang on 2018/3/16.
 */
object ApiService {
    var okHttpClient: OkHttpClient
    var retrofit: Retrofit
    const val BASE_URL = ""
    var apiInterface = mutableMapOf<String, Any>()

    init {
        val mBuilder = OkHttpClient.Builder()
        mBuilder.retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectionPool(ConnectionPool(50, 60, TimeUnit.SECONDS))
                .addNetworkInterceptor {
                    it.proceed(it.request()).let {
                        it.newBuilder().removeHeader("Pragma").header("Cache-Control", it.request().cacheControl().toString()).build()
                    }
                }
                //20M缓存
                .cache(Cache(BaseApplication.context.cacheDir, 20 * 1024 * 1024))
                .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })


        okHttpClient = mBuilder.build()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(
                        RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    inline fun <reified T> api(url: String = BASE_URL): T {
        return if (apiInterface.containsKey(T::class.java.name)) {
            apiInterface[T::class.java.name] as T
        } else {
            if (BASE_URL == url) {
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

}
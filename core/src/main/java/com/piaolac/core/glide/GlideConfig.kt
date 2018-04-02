package com.piaolac.core.glide

/**
 * Created by yangqiang on 2018/3/16.
 */
import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import java.io.File

@GlideModule
class GlideCacheModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val mCacheSize = 200 * 1024 * 1024 //200M缓存大小
        val calculator = MemorySizeCalculator.Builder(context).build()
        val defaultMemoryCacheSize = calculator.memoryCacheSize
        val defaultBitmapPoolSize = calculator.bitmapPoolSize

        val customMemoryCacheSize = (1.2 * defaultMemoryCacheSize).toInt()
        val customBitmapPoolSize = (1.2 * defaultBitmapPoolSize).toInt()

        builder.setMemoryCache(LruResourceCache(customMemoryCacheSize))

        builder.setDiskCache(
                DiskLruCacheFactory(context.cacheDir.toString() + File.separator + "glideCache", mCacheSize))
        builder.setBitmapPool(LruBitmapPool(customBitmapPoolSize))
    }
}

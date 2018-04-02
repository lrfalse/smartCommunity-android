package com.piaolac.core.base

/**
 * Created by yangqiang on 2018/3/19.
 */
interface IViewConfig {
    fun initViewConfig(): ViewBuilder.() -> Unit
}
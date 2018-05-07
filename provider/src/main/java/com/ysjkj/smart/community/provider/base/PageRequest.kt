package com.ysjkj.smart.community.provider.base

/**
 *
 * @author yangqiang
 * @create 2018-05-04 下午4:40
 **/
class PageRequest : HashMap<String, String>() {
    companion object {
        const val ROWS = 15
    }

    var page: Int = 1
        set(value) {
            field = value
            put("page", field.toString())
        }
    var rows: Int = 15
        set(value) {
            field = value
            put("rows", rows.toString())
        }

    init {
        put("page", page.toString())
        put("rows", rows.toString())
    }


}
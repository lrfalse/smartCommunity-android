package com.piaolac.core.utils

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object Utils {

    fun md5(str: String): String {

        try {
            val digest = MessageDigest.getInstance("md5")
            val result = digest.digest(str.toByteArray())
            val buffer = StringBuffer()
            for (b in result) {
                var number = b.toInt() and 0xff     // 加盐
                val md5 = Integer.toHexString(number)
                if (md5.length == 1) {
                    buffer.append("0")
                }
                buffer.append(md5)
            }
            return buffer.toString()        // 标准的md5加密后的结果

        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            return ""
        }

    }

}
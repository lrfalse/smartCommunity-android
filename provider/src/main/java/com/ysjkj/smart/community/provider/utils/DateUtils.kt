package com.ysjkj.smart.community.provider.utils

import org.jetbrains.anko.collections.forEachWithIndex

object DateUtils {
    fun findDay(time: Long): Int {
        return (time / (1000 * 60 * 60 * 24)).toInt()
    }

    fun findHour(time: Long): Int {
        return (time / (1000 * 60 * 60)).toInt()
    }

    fun findMinute(time: Long): Int {
        return (time / (1000 * 60)).toInt()
    }

    fun findSecond(time: Long): Int {
        return (time / (1000)).toInt()
    }

    fun formatCountdownTime(time: Long, format: String = "dd-hh-mm-ss"): String {
        if (format.isNullOrEmpty()) {
            return ""
        }
        return StringBuffer().apply {
            var day = findDay(time)
            var hour = findHour(time - day * (1000 * 60 * 60 * 24))
            var minute = findMinute(time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60))
            var second = findSecond(time - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60) - minute * 1000 * 60)
            format.split("-").forEachWithIndex { _, s ->
                when (s) {
                    "dd" -> {
                        append(day).append("-")
                    }
                    "hh" -> {
                        append(hour).append("-")
                    }
                    "mm" -> {
                        append(minute).append("-")
                    }
                    "ss" -> {
                        append(second).append("-")
                    }
                }
            }
        }.let {
            if (it.length > 1) {
                it.delete(it.length - 1, it.length).toString()
            } else {
                it.toString()
            }
        }

    }

}

fun main(args: Array<String>) {
    DateUtils.formatCountdownTime(1296056931, "dd-hh-mm-ss").apply(::print)
}
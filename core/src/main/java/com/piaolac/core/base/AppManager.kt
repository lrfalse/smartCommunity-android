package com.piaolac.core.base

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import java.util.*

/*
    Activity管理器
 */
object AppManager {

    private val activityStack: Stack<Activity> = Stack()

    /*
        Activity入栈
     */
    fun addActivity(activity: Activity) {
        activityStack.add(activity)
    }

    /*
        Activity出栈
     */
    fun finishActivity(activity: Activity) {
        activity.finish()
        activityStack.remove(activity)
    }

    /*
        获取当前栈顶
     */
    fun currentActivity(): Activity {
        return activityStack.lastElement()
    }

    /*
        清理栈
     */
    fun finishAllActivity() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    /**
     * 关闭其他界面
     */
    fun finishOtherActivitys(activity: Activity) {
        for (ac in activityStack) {
            if (activity != ac) {
                finishActivity(ac)
            }
        }
    }

    /*
        退出应用程序
     */
    fun exitApp(context: Context) {
        finishAllActivity()
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.killBackgroundProcesses(context.packageName)
        System.exit(0)
    }
}

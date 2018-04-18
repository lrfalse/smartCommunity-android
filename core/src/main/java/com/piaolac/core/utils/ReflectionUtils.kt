package com.piaolac.core.utils

import java.lang.reflect.ParameterizedType

/**
 * Created by yangqiang on 2018/3/21.
 */
object ReflectionUtils {
    fun getSuperClassGenricType(clazz: Class<*>, index: Int): Class<*> {

        val genType = clazz.genericSuperclass as? ParameterizedType ?: return Any::class.java

        val params = genType.actualTypeArguments

        if (index >= params.size || index < 0) {
            return Any::class.java
        }
        return if (params[index] !is Class<*>) {
            Any::class.java
        } else params[index] as Class<*>
    }

    fun <T> getSuperClassGenricType(o: Any, i: Int): T? {
        try {
            return ((o.javaClass
                    .genericSuperclass as ParameterizedType).actualTypeArguments[i] as Class<T>)
                    .newInstance()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: ClassCastException) {
            e.printStackTrace()
        }
        return null
    }

    fun <T : Any> getField(o: T, name: String): Any? {
        return o::class.java.getDeclaredField(name)?.let {
            it.isAccessible = true
            it.get(o)
        }
    }
}
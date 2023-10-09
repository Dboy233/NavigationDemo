package com.dboy.navigation.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * - 文件描述：ViewBinding,ViewModel反射创建工具
 * @author Dboy
 * @since 2021/8/17 14:23
 */
object BindingReflexUtil {

    /**
     * 反射获取ViewBinding
     *
     * @param <V>    ViewBinding 实现类
     * @param aClass 当前类
     * @param from   布局膨胀器
     * @return viewBinding实例
    </V> */
    fun <V : ViewBinding> reflexViewBinding(aClass: Class<*>, from: LayoutInflater?): V {
        return try {
            val type = aClass.genericSuperclass
            if (type is ParameterizedType) {
                val actualTypeArguments = type.actualTypeArguments
                for (i in actualTypeArguments.indices) {
                    val tClass: Class<Any> = try {
                        actualTypeArguments[i] as Class<Any>
                    } catch (e: Exception) {
                        continue
                    }
                    if (ViewBinding::class.java.isAssignableFrom(tClass)) {
                        val inflate = tClass.getMethod("inflate", LayoutInflater::class.java)
                        return inflate.invoke(null, from) as V
                    }
                }
            }
            return reflexViewBinding(aClass.superclass, from)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e.message, e)
        }
    }

    /**
     * 反射获取ViewBinding
     *
     * @param <V>    ViewBinding 实现类
     * @param aClass 当前类
     * @param from   布局膨胀器
     * @param b      attachToRoot
     * @return viewBinding实例
    </V> */
    fun <V : ViewBinding> reflexViewBinding(aClass: Class<*>, from: LayoutInflater, viewGroup: ViewGroup?, b: Boolean): V {
        return try {
            val type = aClass.genericSuperclass
            if (type is ParameterizedType) {
                val actualTypeArguments = type.actualTypeArguments
                for (i in actualTypeArguments.indices) {
                    val tClass: Class<Any> = try {
                        actualTypeArguments[i] as Class<Any>
                    } catch (e: java.lang.Exception) {
                        continue
                    }
                    if (ViewBinding::class.java.isAssignableFrom(tClass)) {
                        val inflate = tClass.getDeclaredMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.javaPrimitiveType)
                        return inflate.invoke(null, from, viewGroup, b) as V
                    }
                }
            }
            return reflexViewBinding<ViewBinding>(aClass.superclass, from, viewGroup, b) as V
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            throw java.lang.RuntimeException(e.message, e)
        }
    }


    /**
     * 反射获取ViewModel
     *
     * @param <VM>   ViewModel实现类
     * @param aClass 当前class
     * @param owner  生命周期管理
     * @return ViewModel实例
    </VM> */
    fun <VM : ViewModel> reflexViewModel(aClass: Class<*>, owner: ViewModelStoreOwner): VM {
        return try {
            return ViewModelProvider(owner).get(reflectViewModelClass(aClass))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            throw java.lang.RuntimeException(e.message, e)
        }
    }

    /**
     * 获取Class中携带的具体泛型对象泛型具体类
     */
    fun <VM : ViewModel> reflectViewModelClass(aClass: Class<*>): Class<VM> {
        return try {
            val type = aClass.genericSuperclass
            if (type is ParameterizedType) {
                val actualTypeArguments = type.actualTypeArguments
                for (i in actualTypeArguments.indices) {
                    val aClass1: Class<Any> = try {
                        actualTypeArguments[i] as Class<Any>
                    } catch (e: java.lang.Exception) {
                        continue
                    }
                    if (ViewModel::class.java.isAssignableFrom(aClass1)) {
                        return aClass1 as Class<VM>
                    }
                }
            }
            return reflectViewModelClass(aClass.superclass)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            throw java.lang.RuntimeException(e.message, e)
        }
    }


}
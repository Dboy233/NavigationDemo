package com.dboy.navigation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import java.lang.reflect.ParameterizedType
import java.util.*

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
abstract class BaseFragment<VM : ViewModel> : Fragment() {

    protected val viewModel: VM by lazy {
        reflexViewModel(javaClass, this)
    }

    /**
     * 页面LogTag
     */
    val TAG = "NavigationDemo"

    /**
     * 生命周期Tag
     */
     val  TAG_LIFE = "FragmentLife"

    abstract val layoutId: Int


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG_LIFE, "${javaClass.simpleName} onViewCreated")
        initViewAndData(view)
        initLiveData()
    }

    abstract fun initViewAndData(view: View)

    abstract fun initLiveData()


    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(TAG_LIFE, "${javaClass.simpleName} onDestroyView ")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG_LIFE, "${javaClass.simpleName} onDestroy ")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG_LIFE, "${javaClass.simpleName} onSaveInstanceState: $outState")
    }

    override fun setInitialSavedState(state: SavedState?) {
        super.setInitialSavedState(state)
        Log.d(TAG_LIFE, "${javaClass.simpleName}  setInitialSavedState: $state")
    }

    /**
     * 使用反射初始化ViewModel,如果比较喜欢这个方式的话请参照
     * https://github.com/Quyunshuo/AndroidBaseFrameMVVM/blob/master/Lib_Base/src/main/java/com/quyunshuo/base/utils/BindingReflex.kt
     * 这个也是我参与完成的，有一整套的反射方式。
     */
    open fun <VM : ViewModel> reflexViewModel(aClass: Class<*>?, owner: ViewModelStoreOwner): VM {
        try {
            val actualTypeArguments = (Objects.requireNonNull(
                aClass?.genericSuperclass
            ) as ParameterizedType).actualTypeArguments
            for (i in actualTypeArguments.indices) {
                val tClass: Class<Any> = actualTypeArguments[i] as Class<Any>
                if (ViewModel::class.java.isAssignableFrom(tClass)) {
                    return ViewModelProvider(owner)[tClass as Class<VM>]
                }
            }
            return reflexViewModel<VM>(aClass?.superclass, owner)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        throw RuntimeException("失败")
    }




    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                animation.setAnimationListener(object : Animation.AnimationListener{
                    override fun onAnimationStart(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        onEnterAnimEnd()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                })
                return animation
            } else {
                onEnterAnimEnd()
            }
        } else {
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                //延迟100毫秒执行让View有一个初始化的时间，防止初始化时刷新页面与动画刷新冲突造成卡顿
                animation.startOffset = 100
                return animation
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }



    fun onEnterAnimEnd(){

    }

}
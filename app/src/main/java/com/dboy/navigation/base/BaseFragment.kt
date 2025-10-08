package com.dboy.navigation.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.view.OneShotPreDrawListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.dboy.navigation.fragment.viewmodel.BaseViewModel
import com.dboy.navigation.utils.ReflexUtil

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
abstract class BaseFragment<VB: ViewBinding,VM : BaseViewModel> : Fragment() {

    protected val viewModel: VM by lazy {
        ReflexUtil.reflexViewModel(javaClass, this)
    }

    protected val viewBinding:VB by lazy {
        ReflexUtil.reflexViewBinding(javaClass,layoutInflater)
    }

    /**
     * 页面LogTag
     */
    val TAG = "NavigationDemo"

    /**
     * 生命周期Tag
     */
    val TAG_LIFE = "FragmentLife"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        initViewAndData(view)
        initLiveData()
        //方法1  扩展函数doOnPreDraw
//        (view.parent as? ViewGroup)?.doOnPreDraw {
//            startPostponedEnterTransition()
//        }
        //方法2 doOnPreDraw = OneShotPreDrawListener.add()
        (view.parent as? ViewGroup)?.apply {
            OneShotPreDrawListener.add(this) {
                startPostponedEnterTransition()
            }
        }
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


    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (enter) {
            if (nextAnim > 0) {
                val animation = AnimationUtils.loadAnimation(requireActivity(), nextAnim)
                animation.setAnimationListener(object : Animation.AnimationListener {
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
                return AnimationUtils.loadAnimation(requireActivity(), nextAnim)
            }
        }
        return super.onCreateAnimation(transit, enter, nextAnim)
    }

    fun onEnterAnimEnd() {
        Log.d(TAG, "onEnterAnimEnd: ")
    }

}
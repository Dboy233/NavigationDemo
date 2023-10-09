package com.dboy.navigation.fragment

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.R
import com.dboy.navigation.base.BaseFragment
import com.dboy.navigation.databinding.FragmentHomeBinding
import com.dboy.navigation.fragment.viewmodel.HomeViewModel

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :首页
 */
class HomeFragment : BaseFragment<HomeViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_home

    val  viewBinding :FragmentHomeBinding by lazy {
        FragmentHomeBinding.bind(requireView())
    }
    override fun initViewAndData(view: View) {

        //其中一种跳转页面方式
//        go_my_button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.myFragment))

        //通过插件生成页面携参帮助类
        view.findViewById<Button>(R.id.go_my_button).setOnClickListener {
            //插件会生成一个包装类，这个类里面记录了当前页面到下个页面（具体看名字）所携带的变量参数
            //如果当前fragment可能有多个子页面节点 在改类中会生成多个静态方法用于传递参数
            val actionHomeFragmentToMyFragment =
                HomeFragmentDirections.actionHomeFragmentToMyFragment()
            actionHomeFragmentToMyFragment.name = "Dboy"
            findNavController().navigate(actionHomeFragmentToMyFragment)
        }

        //设置数据按钮
        view.findViewById<Button>(R.id.set_data).setOnClickListener {
            viewModel.initInfo("（＞人＜；）")
        }
    }

    override fun initLiveData() {
        viewModel.liveData.observe(this) {
            Log.d(TAG, "data change :  $it ")
            viewBinding.textView.text = it
        }
    }
}
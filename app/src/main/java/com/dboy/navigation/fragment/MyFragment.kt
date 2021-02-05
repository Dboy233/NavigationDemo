package com.dboy.navigation.fragment

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.R
import com.dboy.navigation.adapter.UserInfoAdapter
import com.dboy.navigation.base.BaseFragment
import com.dboy.navigation.fragment.viewmodel.MyViewModel
import com.dboy.navigation.livedata.LiveDataStatus.*
import kotlinx.android.synthetic.main.fragment_my.*

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :首页
 */
class MyFragment : BaseFragment<MyViewModel>() {

    lateinit var adapter: UserInfoAdapter

    override val layoutId: Int
        get() = R.layout.fragment_my

    override fun initViewAndData(view: View) {
        //插件会生成 一个 fragment+Args字样的帮助类 通过它可以获取传递的Bundle值
        val name = MyFragmentArgs.fromBundle(requireArguments()).name
        name.apply {
            Toast.makeText(context, "Arguments: $this", Toast.LENGTH_SHORT).show()
        }
        //进入设置页面点击事件
        next_pager_btn.setOnClickListener {
            findNavController().navigate(MyFragmentDirections.actionMyFragmentToSettingFragment())
        }
        //刷新数据点击事件
        refresh_data_btn.setOnClickListener {
            viewModel.initUserInfo()
        }
        //初始化列表 layoutManager写在了布局中
        adapter = UserInfoAdapter(R.layout.item_user_info_layout)
        user_info_rv.adapter = adapter
    }

    override fun initLiveData() {
//        viewModel.userInfoLiveData.observe(this,{
//                 adapter.setNewInstance(it)
//        },{
//
//        })
        viewModel.userInfoLiveData.observe(this) {
            Log.d(TAG, "initLiveData: onChange")
            adapter.setNewInstance(it)
        }
        viewModel.userInfoLiveData.observeStatus(this) {
            when (it) {
                START -> Log.d(TAG, "数据开始请求")
                SUCCESS -> {
                    Log.d(TAG, "数据请求 成功")
                    Toast.makeText(requireContext(), "数据请求 成功", Toast.LENGTH_SHORT).show()
                }
                ERROR -> {
                    Log.d(TAG, "数据请求 失败")
                    Toast.makeText(requireContext(), "数据请求失败", Toast.LENGTH_SHORT).show()
                }
                COMPLETE -> Log.d(TAG, "数据请求 完成")
                RESET -> {
                    Log.d(TAG, "需要重置数据")
                    viewModel.initUserInfo()
                }
            }
        }
    }
}
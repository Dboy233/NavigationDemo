package com.dboy.navigation.fragment

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.R
import com.dboy.navigation.base.BaseFragment
import com.dboy.navigation.databinding.FragmentSettingBinding
import com.dboy.navigation.fragment.viewmodel.SettingViewModel

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
class SettingFragment : BaseFragment<SettingViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_setting

    lateinit var viewBinding:FragmentSettingBinding

    override fun initViewAndData(view: View) {
        viewBinding=  FragmentSettingBinding.bind(view)

        viewBinding.goBackToHome.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
        viewBinding.setData.setOnClickListener {
            viewModel.initInfo("щ(ʘ╻ʘ)щ")
        }
        viewBinding.openSelf.setOnClickListener {
            findNavController().navigate(R.id.action_settingFragment_global)
        }
        requireActivity().onBackPressedDispatcher.addCallback {
            findNavController().popBackStack()
        }

    }

    override fun initLiveData() {
        viewModel.liveData.observe(this) {
            Log.d(TAG, "data change: $it")
            viewBinding.textView.text = it
        }
    }

}
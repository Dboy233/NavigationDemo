package com.dboy.navigation.fragment

import android.util.Log
import android.view.View
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.dboy.navigation.R
import com.dboy.navigation.base.BaseFragment
import com.dboy.navigation.fragment.viewmodel.SettingViewModel
import kotlinx.android.synthetic.main.fragment_setting.*

/**
 *   @author DBoy
 *   @date 2020/9/2
 *   Class 描述 :
 */
class SettingFragment : BaseFragment<SettingViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_setting

    override fun initViewAndData(view: View) {
        view.findViewById<View>(R.id.go_back_to_home).setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
        }
        view.findViewById<Button>(R.id.set_data).setOnClickListener {
            viewModel.initInfo("щ(ʘ╻ʘ)щ")
        }
    }

    override fun initLiveData() {
        viewModel.liveData.observe(this) {
            Log.d(TAG, "data change: $it")
            textView.text = it
        }
    }


}
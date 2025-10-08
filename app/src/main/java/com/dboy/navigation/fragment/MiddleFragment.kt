package com.dboy.navigation.fragment

import androidx.viewbinding.ViewBinding
import com.dboy.navigation.base.BaseFragment
import com.dboy.navigation.fragment.viewmodel.BaseViewModel

abstract class MiddleFragment<VM : BaseViewModel, VB : ViewBinding> : BaseFragment<VB, VM>() {

    override fun onDestroyView() {
        super.onDestroyView()

    }
}
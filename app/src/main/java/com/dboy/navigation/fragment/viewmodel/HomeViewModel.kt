package com.dboy.navigation.fragment.viewmodel

import androidx.lifecycle.MutableLiveData

/**
 * @author DBoy
 * @date 2020/12/20
 * Class 描述 :
 */
class HomeViewModel : BaseViewModel() {

    var liveData = MutableLiveData<String>()

    /**
     * 初始化信息
     */
    fun initInfo(msg: String?) {
        liveData.value = "Home LiveData save Data{ $msg }"
    }
}
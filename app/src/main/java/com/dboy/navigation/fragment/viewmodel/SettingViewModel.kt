package com.dboy.navigation.fragment.viewmodel

import androidx.lifecycle.MutableLiveData

/**
 * @author DBoy
 * @date 2020/12/20
 * Class 描述 :
 */
class SettingViewModel : BaseViewModel() {

    var liveData = MutableLiveData<String>()

    fun initInfo(msg: String) {
        liveData.value = "Setting LiveData save data { $msg }"
    }

}
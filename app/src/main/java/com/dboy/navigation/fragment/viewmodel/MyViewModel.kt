package com.dboy.navigation.fragment.viewmodel

import com.dboy.navigation.data.UserInfo
import com.dboy.navigation.fragment.model.DataCallBack
import com.dboy.navigation.fragment.model.DataTestModel
import com.dboy.navigation.livedata.MutableLiveDataStatus

/**
 * @author DBoy
 * @date 2020/12/20
 * Class 描述 : 我的页面ViewModel
 */
class MyViewModel : BaseViewModel() {

    /**
     * 测试RepositoryModel
     */
    private val testModel = DataTestModel()

    /**
     * 用户列表活动数据
     */
    var userInfoLiveData = MutableLiveDataStatus<MutableList<UserInfo>>()

    /**
     * 是否要数据请求成功
     */
    private var canSuccess = true

    /**
     * 初始化用户信息
     */
    fun initUserInfo() {
        //我们在获取数据前通知数据准备开始改变了
        userInfoLiveData.setStart()
        //我这里为了方便查看效果使用了简陋的方式获取数据，可以采用Flow RxJava,整体流程都一样。
        testModel.getUserData(canSuccess, object : DataCallBack<MutableList<UserInfo>> {
            override fun success(data: MutableList<UserInfo>) {
                //当数据能够成功获取到,我们拿到的数据可能不是完全的符合UI展示所以需要在ViewModel中对数据进行处理；
                //这里就是做了一个筛选操作。
                //最终我们将数据交给LiveData,并由LiveData通知UI更新数据
                userInfoLiveData.value = data.filter {
                    it.userName != "未知" && it.userSex != "未知"
                }.toMutableList()
            }

            override fun failure(message: String?) {
                //当然我们也可以清空数据
//                userInfoLiveData.value = null
                userInfoLiveData.setError(message)
            }
        })

        canSuccess = !canSuccess
    }


    override fun onCleared() {
        super.onCleared()
        testModel.destory()
    }

}
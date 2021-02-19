package com.dboy.navigation.fragment.model

import android.util.Log
import com.dboy.navigation.data.UserInfo

/**
 * @author DBoy
 * @date 2021/2/5
 * Class 描述 : 测试RepositoryModel
 */
class DataTestModel {
    /**
     * 模拟获取用户数据，可能是从数据库，可能是从网络
     *
     * @param canRequestSuccess 是否模拟成功获取
     * @param dataCallBack      数据回调
     */
    fun getUserData(canRequestSuccess: Boolean, dataCallBack: DataCallBack<MutableList<UserInfo>>) {
        if (!canRequestSuccess) {
            dataCallBack.failure("数据获取失败了")
            return
        }
        val userInfos: MutableList<UserInfo> = mutableListOf()
        //不需要的数据
        userInfos.add(UserInfo("未知", "不男不女"))
        for (i in 0..100) {
            userInfos.add(UserInfo("用户$i", if (i % 2 == 0) "男" else "女"))
        }
        //不需要的数据
        userInfos.add(UserInfo("啊猫", "未知"))
        dataCallBack.success(userInfos)
    }

    /**
     * 销毁
     */
    fun destory() {
        Log.d("DBoy", "TestModel 被销毁了")
    }
}
package com.dboy.navigation.adapter

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.dboy.navigation.R
import com.dboy.navigation.data.UserInfo

/**
 *   @author DBoy
 *   @date 2021/2/5
 *   Class 描述 :
 */
class UserInfoAdapter(layoutResId: Int) : BaseQuickAdapter<UserInfo, BaseViewHolder>(layoutResId) {
    override fun convert(holder: BaseViewHolder, item: UserInfo) {
        Log.d("NavigationDemo", "item create: ${item.toString()}")
        holder.setText(R.id.item_user_name, item.userName)
            .setText(R.id.item_user_sex, item.userSex)
    }

}
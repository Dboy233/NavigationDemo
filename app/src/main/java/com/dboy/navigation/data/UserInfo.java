package com.dboy.navigation.data;

/**
 * @author DBoy
 * @date 2021/2/5
 * Class 描述 : 用户信息
 */
public class UserInfo {

    public String userName;

    public String userSex;

    public UserInfo(String userName, String userSex) {
        this.userName = userName;
        this.userSex = userSex;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "userName='" + userName + '\'' +
                '}';
    }
}



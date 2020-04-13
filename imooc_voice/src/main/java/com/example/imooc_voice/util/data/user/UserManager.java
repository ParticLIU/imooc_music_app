package com.example.imooc_voice.util.data.user;

/**
 * @author xiaoyu.liu
 * create at 2020/4/9
 * description: 单例模式管理登陆用户信息
 */
public class UserManager {
    private volatile static UserManager singleton = null;
    private User mUser;

    public static UserManager getInstance(){
            if(singleton == null){
                synchronized (UserManager.class){
                    if(singleton == null){//双检查机制
                        singleton = new UserManager();
                    }
                }
            }
            return singleton;
        }

    private UserManager(){

    }

    //保存用户信息
    public void saveUser(User user){
        mUser = user;
        localUser(user);
    }

    //持久化用户信息
    private void localUser(User user){

    }

    //获取用户信息
    public User getUser(){
        return mUser;
    }

    //从本地获取用户信息
    private User getLocalUser(){
        return null;
    }

    //是否登陆
    public boolean hasLogin(){
        return getUser() == null ? false : true;
    }

    public void removeUser(){
        mUser = null;
        removeLocalUser();
    }

    private void removeLocalUser(){

    }


}

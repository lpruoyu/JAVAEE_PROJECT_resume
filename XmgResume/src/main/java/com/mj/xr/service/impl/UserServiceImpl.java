package com.mj.xr.service.impl;

import com.mj.xr.bean.User;
import com.mj.xr.dao.UserDao;
import com.mj.xr.service.UserService;


public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public User get(User user) {
        return ((UserDao) dao).get(user);
    }
}

package programmer.lp.resume.service.impl;

import programmer.lp.resume.base.BaseServiceImpl;
import programmer.lp.resume.bean.User;
import programmer.lp.resume.dao.UserDao;
import programmer.lp.resume.service.UserService;

public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public User login(User user) {
        UserDao userDao = (UserDao) dao();
        return userDao.get(user);
    }

}
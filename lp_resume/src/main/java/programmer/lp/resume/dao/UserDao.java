package programmer.lp.resume.dao;

import programmer.lp.resume.base.BaseDao;
import programmer.lp.resume.bean.User;

public interface UserDao extends BaseDao<User> {

    User get(User user);

}
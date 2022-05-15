package programmer.lp.resume.service;

import programmer.lp.resume.base.BaseService;
import programmer.lp.resume.bean.User;

public interface UserService extends BaseService<User> {

    User login(User user);

}
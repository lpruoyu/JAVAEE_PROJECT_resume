package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.User;
import programmer.lp.resume.dao.UserDao;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

    @Override
    public boolean save(User bean) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(bean.getPassword());
        args.add(bean.getEmail());
        args.add(bean.getBirthday());
        args.add(bean.getPhoto());
        args.add(bean.getIntro());
        args.add(bean.getName());
        args.add(bean.getAddress());
        args.add(bean.getPhone());
        args.add(bean.getJob());
        args.add(bean.getTrait());
        args.add(bean.getInterests());

        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(password, email, birthday, photo, intro, name, address, phone, job, trait, interests) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET password = ?, email = ?, birthday = ?, photo = ?, intro = ?, name = ?, address = ?, phone = ?, job = ?, trait = ?, interests = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<User> list() {
        final String sql = "SELECT id, created_time, last_update_time, password, email, birthday, photo, intro, name, address, phone, job, trait, interests FROM " + tableName();
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(User.class));
    }

    @Override
    public User get(User user) {
        final String sql = "SELECT id, created_time, last_update_time, password, email, birthday, photo, intro, name, address, phone, job, trait, interests FROM " + tableName() + " WHERE email = ? AND password = ?";
        final List<User> query = JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(User.class), user.getEmail(), user.getPassword());
        if (query.isEmpty()) return null;
        return query.get(0);
    }

}
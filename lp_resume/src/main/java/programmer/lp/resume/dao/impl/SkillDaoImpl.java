package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Skill;
import programmer.lp.resume.dao.SkillDao;

import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends BaseDaoImpl<Skill> implements SkillDao {

    @Override
    public boolean save(Skill bean) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(bean.getName());
        args.add(bean.getLevel());
        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(name, level) VALUES(?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET name = ?, level = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Skill> list() {
        final String sql = "SELECT id, created_time, last_update_time, name, level FROM " + tableName();
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(Skill.class));
    }
//
//    @Override
//    protected String tableName() {
//        return "skill";
//    }

}

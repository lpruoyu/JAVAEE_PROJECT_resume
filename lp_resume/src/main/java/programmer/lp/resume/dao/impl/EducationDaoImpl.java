package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Education;
import programmer.lp.resume.dao.EducationDao;

import java.util.ArrayList;
import java.util.List;

public class EducationDaoImpl extends BaseDaoImpl<Education> implements EducationDao {

    @Override
    public boolean save(Education education) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(education.getName());
        args.add(education.getType());
        args.add(education.getIntro());
        args.add(education.getBeginDay());
        args.add(education.getEndDay());
        Integer id = education.getId();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO education(name, type, intro, begin_day, end_day) VALUES(?, ?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE education SET name = ?, type = ?, intro = ?, begin_day = ?, end_day = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Education> list() {
        String sql = "SELECT id, created_time, last_update_time, name, type, intro, begin_day, end_day FROM education ORDER BY type ASC";
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(Education.class));
    }

//    @Override
//    protected String tableName() {
//        return "education";
//    }

}

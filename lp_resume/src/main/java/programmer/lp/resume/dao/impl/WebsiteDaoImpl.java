package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Website;
import programmer.lp.resume.dao.WebsiteDao;

import java.util.ArrayList;
import java.util.List;

public class WebsiteDaoImpl extends BaseDaoImpl<Website> implements WebsiteDao {

    @Override
    public boolean save(Website website) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(website.getFooter());
        final Integer id = website.getId();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO website(footer) VALUES(?)";
        } else { // 更新该条数据
            sql = "UPDATE website SET footer = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Website> list() {
        String sql = "SELECT id, created_time, last_update_time ,footer FROM website";
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(Website.class));
    }
//
//    @Override
//    protected String tableName() {
//        return "website";
//    }

}

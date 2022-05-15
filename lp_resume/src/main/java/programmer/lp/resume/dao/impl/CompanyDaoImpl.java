package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.dao.CompanyDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompanyDaoImpl extends BaseDaoImpl<Company> implements CompanyDao {

    @Override
    public boolean save(Company bean) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(bean.getName());
        args.add(bean.getLogo());
        args.add(bean.getWebsite());
        args.add(bean.getIntro());
        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(name, logo, website, intro) VALUES(?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET name = ?, logo = ?, website = ?, intro = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Company> list() {
        final String sql = "SELECT id, created_time, last_update_time, name, logo, website, intro FROM " + tableName();
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(Company.class));
    }


    public List<String> list(Integer[] ids) {
        List<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT logo FROM ").append(tableName()).append(" WHERE id IN (");
        for (int i = 0; i < ids.length; i++) {
            if (i != 0) {
                sb.append(", ");
            }
            sb.append("?");
            list.add(ids[i]);
        }
        sb.append(")");

        final List<Map<String, Object>> maps = JDBCTEMPLATE.queryForList(sb.toString(), list.toArray());

        List<String> strings = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            strings.add((String) map.get("logo"));
        }

        return strings;
//        return JDBCTEMPLATE.queryForList(sb.toString(), list.toArray());
    }

//    @Override
//    protected String tableName() {
//        return "company";
//    }

}

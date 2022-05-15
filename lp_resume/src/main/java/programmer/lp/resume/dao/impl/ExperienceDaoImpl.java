package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.bean.Experience;
import programmer.lp.resume.dao.ExperienceDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExperienceDaoImpl extends BaseDaoImpl<Experience> implements ExperienceDao {

    public static final String QUERY_STRING;
    public static final RowMapper<Experience> ROW_MAPPER;

    static {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("t1.job, t1.intro, t1.begin_day, t1.end_day, t1.id, ")
                .append("t2.name, t2.logo, t2.website, t2.intro, t2.id ")
                .append("FROM experience t1 ")
                .append("JOIN company t2 ")
                .append("ON t1.company_id = t2.id");
        QUERY_STRING = sb.toString();

        ROW_MAPPER = new RowMapper<Experience>() {
            @Override
            public Experience mapRow(ResultSet resultSet, int i) throws SQLException {
                Experience experience = new Experience();
                Company company = new Company();
                experience.setCompany(company);
                experience.setIntro(resultSet.getString("t1.intro"));
                experience.setJob(resultSet.getString("t1.job"));
                experience.setId(Integer.valueOf(resultSet.getString("t1.id")));
                experience.setBeginDay(resultSet.getDate("t1.begin_day"));
                experience.setEndDay(resultSet.getDate("t1.end_day"));
                company.setLogo(resultSet.getString("t2.logo"));
                company.setIntro(resultSet.getString("t2.intro"));
                company.setName(resultSet.getString("t2.name"));
                company.setId(Integer.valueOf(resultSet.getString("t2.id")));
                company.setWebsite(resultSet.getString("t2.website"));
                return experience;
            }
        };

    }

    @Override
    public boolean save(Experience bean) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(bean.getJob());
        args.add(bean.getIntro());
        args.add(bean.getBeginDay());
        args.add(bean.getEndDay());
        args.add(bean.getCompany().getId());
        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(job, intro, begin_day, end_day, company_id) VALUES(?, ?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET job = ?, intro = ?, begin_day = ?, end_day = ?, company_id = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Experience> list() {
        return JDBCTEMPLATE.query(QUERY_STRING, ROW_MAPPER);
    }

}

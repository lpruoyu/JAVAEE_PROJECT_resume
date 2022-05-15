package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.RowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.bean.Project;
import programmer.lp.resume.dao.ProjectDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProjectDaoImpl extends BaseDaoImpl<Project> implements ProjectDao {

    public static final String QUERY_STRING;
    public static final RowMapper<Project> ROW_MAPPER;

    static {
        final StringBuilder sb = new StringBuilder();
        sb.append("SELECT ")
                .append("t1.name, t1.intro, t1.website, t1.image, t1.begin_day, t1.end_day, t1.id, ")
                .append("t2.name, t2.logo, t2.website, t2.intro, t2.id ")
                .append("FROM project t1 ")
                .append("JOIN company t2 ")
                .append("ON t1.company_id = t2.id");
        QUERY_STRING = sb.toString();

        ROW_MAPPER = new RowMapper<Project>() {
            @Override
            public Project mapRow(ResultSet resultSet, int i) throws SQLException {
                Project project = new Project();
                Company company = new Company();
                project.setCompany(company);
                project.setIntro(resultSet.getString("t1.intro"));
                project.setName(resultSet.getString("t1.name"));
                project.setWebsite(resultSet.getString("t1.website"));
                project.setImage(resultSet.getString("t1.image"));
                project.setId(Integer.valueOf(resultSet.getString("t1.id")));
                project.setBeginDay(resultSet.getDate("t1.begin_day"));
                project.setEndDay(resultSet.getDate("t1.end_day"));

                company.setLogo(resultSet.getString("t2.logo"));
                company.setIntro(resultSet.getString("t2.intro"));
                company.setName(resultSet.getString("t2.name"));
                company.setId(Integer.valueOf(resultSet.getString("t2.id")));
                company.setWebsite(resultSet.getString("t2.website"));
                return project;
            }
        };

    }

    @Override
    public boolean save(Project bean) {
        String sql;
        List<Object> args = new ArrayList<>();

        args.add(bean.getName());
        args.add(bean.getIntro());
        args.add(bean.getWebsite());
        args.add(bean.getImage());
        args.add(bean.getBeginDay());
        args.add(bean.getEndDay());
        args.add(bean.getCompany().getId());

        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(name, intro, website, image, begin_day, end_day, company_id) VALUES(?, ?, ?, ?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET name = ?, intro = ?, website = ?, image = ?, begin_day = ?, end_day = ?, company_id = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Project> list() {
        return JDBCTEMPLATE.query(QUERY_STRING, ROW_MAPPER);
    }

    public List<String> list(Integer[] ids) {
        List<Integer> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT image FROM ").append(tableName()).append(" WHERE id IN (");
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
            strings.add((String) map.get("image"));
        }
        return strings;
    }

}

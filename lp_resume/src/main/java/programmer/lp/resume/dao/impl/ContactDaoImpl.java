package programmer.lp.resume.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import programmer.lp.resume.base.BaseDaoImpl;
import programmer.lp.resume.bean.Contact;
import programmer.lp.resume.bean.ContactResult;
import programmer.lp.resume.dao.ContactDao;
import programmer.lp.resume.util.Strings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ContactDaoImpl extends BaseDaoImpl<Contact> implements ContactDao {

    @Override
    public boolean save(Contact bean) {
        String sql;
        List<Object> args = new ArrayList<>();
        args.add(bean.getName());
        args.add(bean.getEmail());
        args.add(bean.getComment());
        args.add(bean.getSubject());
        args.add(bean.getAlreadyRead());
        args.add(bean.getCreatedTime());
        final Integer id = bean.getId();
        final String tableName = tableName();
        if (id == null || id < 1) { // 插入一条数据
            sql = "INSERT INTO " + tableName + "(name, email, comment, subject, already_read, created_time) VALUES(?, ?, ?, ?, ?, ?)";
        } else { // 更新该条数据
            sql = "UPDATE " + tableName + " SET name = ?, email = ?, comment = ?, subject = ?, already_read = ?, created_time = ? WHERE id = ?";
            args.add(id);
        }
        return JDBCTEMPLATE.update(sql, args.toArray()) == 1;
    }

    @Override
    public List<Contact> list() {
        final String sql = "SELECT id, created_time, last_update_time, name, email, comment, subject, already_read FROM " + tableName();
        return JDBCTEMPLATE.query(sql, new BeanPropertyRowMapper<>(Contact.class));
    }

    public ContactResult list(Contact.Search search) {

        ContactResult result = new ContactResult();

        // TODO 在此处更改Search默认值
        StringBuilder sb = new StringBuilder(); // 查询Contacts
        StringBuilder countSQL = new StringBuilder(); // 查询总条数
        StringBuilder condition = new StringBuilder(); // 查询的附加条件
        List<Object> args = new ArrayList<>(); // 查询的参数

        sb.append("SELECT id, created_time, last_update_time, name, email, comment, subject, already_read FROM ");
        sb.append(tableName());
        sb.append(" WHERE 1 = 1 "); // 技巧

        countSQL.append("SELECT COUNT(*) FROM ");
        countSQL.append(tableName());
        countSQL.append(" WHERE 1 = 1 "); // 技巧

//        日期查询：左闭右开

        Date beginDay = search.beginDay;
        if (null != beginDay) {
            condition.append("AND created_time >= ? ");
            args.add(beginDay);
        }

        Date endDay = search.endDay;
        if (null != endDay) {
            condition.append("AND created_time <= ? ");
            args.add(endDay);
        }

        if (Strings.isNotNull(search.keyWord)) {
            condition.append("AND (name LIKE ? OR email LIKE ? OR comment LIKE ? OR subject LIKE ?) ");
            final String keyWord = "%" + search.keyWord + "%";
            args.add(keyWord);
            args.add(keyWord);
            args.add(keyWord);
            args.add(keyWord);
        }

        if (search.alreadyRead < 2) {
            condition.append("AND already_read = ? ");
            args.add(search.alreadyRead);
        }

        countSQL.append(condition);

        // 数据总条数
        final Integer count = JDBCTEMPLATE.queryForObject(countSQL.toString(), Integer.class, args.toArray());

        if (count != null && count > 0) {
            result.setTotalSize(count);
            final Integer pageSize = search.getPageSize();
             /*
                总数量：101
                每一页显示20条
                公式：总页数 = (总数量 +  每页的数量 - 1) / 每页的数量
                            = ( 101   +   20       - 1) / 20
             */
            final int totalPage = (count + pageSize - 1) / pageSize; // 总页数
            result.setTotalPage(totalPage);
            if (search.pageIndex > totalPage) {
                search.pageIndex = totalPage;
            }
        } else { // 没有符合要求的结果
            /* 不用设置也可以，因为都是默认值
            result.setTotalSize(0);
            result.setTotalPage(0);
            result.setContacts(null);
            */
            result.setSearch(search);
            return result;
        }

        sb.append(condition);
        sb.append("LIMIT ?, ?");
        args.add((search.pageIndex - 1) * search.pageSize);
        args.add(search.pageSize);

        result.setSearch(search);
        result.setContacts(JDBCTEMPLATE.query(sb.toString(), new BeanPropertyRowMapper<>(Contact.class), args.toArray()));
        return result;
    }

    @Override
    public boolean read(Integer id) {
        String sql = "UPDATE " + tableName() + " SET already_read = 1 WHERE id = ?";
        return JDBCTEMPLATE.update(sql, id) == 1;
    }

}
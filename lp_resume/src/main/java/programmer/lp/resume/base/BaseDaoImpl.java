package programmer.lp.resume.base;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import programmer.lp.resume.dao.impl.WebsiteDaoImpl;
import programmer.lp.resume.util.Strings;

import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public abstract class BaseDaoImpl<T extends BaseBean> implements BaseDao<T> {

    protected static JdbcTemplate JDBCTEMPLATE;

    static {
        try (InputStream is = WebsiteDaoImpl.class.getClassLoader().getResourceAsStream("druid.properties")) {
            Properties properties = new Properties();
            properties.load(is);
            JDBCTEMPLATE = new JdbcTemplate(DruidDataSourceFactory.createDataSource(properties));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final String tableName = generateTableName();

    protected String tableName() {
        return tableName;
    }

    private String generateTableName() {
        final ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        final Class type = (Class) genericSuperclass.getActualTypeArguments()[0];
        final String simpleName = type.getSimpleName();
        return Strings.underLineCase(simpleName);
    }

//    protected abstract String tableName;

    @Override
    public Integer count() {
        final String sql = "SELECT COUNT(*) FROM " + tableName;
        return JDBCTEMPLATE.queryForObject(sql, new BeanPropertyRowMapper<>(Integer.class));
    }

    @Override
    public boolean remove(Integer id) {
        return removeAll(new Integer[]{id});
    }

//    public boolean remove(Integer id) {
//        String sql = "DELETE FROM education WHERE id = ?";
//        return JDBCTEMPLATE.update(sql, id) == 1;
//    }

    @Override
    public boolean removeAll(Integer[] ids) {
        List<Integer> args = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ").append(tableName).append(" WHERE id in (");
        for (Integer id : ids) {
            args.add(id);
            sb.append("?, ");
        }
        sb.replace(sb.length() - 2, sb.length(), ")");
        return JDBCTEMPLATE.update(sb.toString(), args.toArray()) > 0;
    }

    @Override
    public boolean update(T base) {
        return false;
    }

}

package programmer.lp.resume.base;

import java.util.List;

public interface BaseDao<T extends BaseBean> {

// 此方法不严谨
//    default String tableName() {
//        final String name = this.getClass().getSimpleName();
//        final String dao = name.substring(0, name.indexOf("Dao"));
//        // return Strings.underLineCase(dao);
//-------------------------------------------------------------------------------
//        只能针对表名为一个单词的
//        final char[] chars = dao.toCharArray();
//        // A 65
//        // a 97
//        chars[0] += 32;
//        // return new String(chars);
//    }

    boolean save(T bean);

    boolean remove(Integer id);

    boolean removeAll(Integer[] ids);

    boolean update(T bean);

    List<T> list();

    Integer count();

}
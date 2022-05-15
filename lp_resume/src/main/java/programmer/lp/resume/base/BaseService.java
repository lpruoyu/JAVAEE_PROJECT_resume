package programmer.lp.resume.base;

import java.util.List;

public interface BaseService<T extends BaseBean> {

    boolean save(T bean);

    boolean remove(Integer id);

    boolean removeAll(Integer[] ids);

    boolean update(T bean);

    List<T> list();

    Integer count();

}

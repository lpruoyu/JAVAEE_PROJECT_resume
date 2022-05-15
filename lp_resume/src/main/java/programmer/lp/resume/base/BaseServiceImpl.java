package programmer.lp.resume.base;

import java.util.List;

public abstract class BaseServiceImpl<T extends BaseBean> implements BaseService<T> {

    private final BaseDao<T> dao = newDao();

    protected BaseDao<T> dao() {
        return dao;
    }

    protected BaseDao<T> newDao() {

        // programmer.lp.resume.service.impl.AwardServiceImpl
        // programmer.lp.resume.dao.impl.AwardDaoImpl

        String name = this.getClass().getName()
                .replace(".service.", ".dao.")
                .replace("ServiceImpl", "DaoImpl");
        try {
            return (BaseDao<T>) Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    protected abstract BaseDao<T> dao();

    @Override
    public boolean save(T bean) {
        return dao.save(bean);
    }

    @Override
    public boolean remove(Integer id) {
        return dao.remove(id);
    }

    @Override
    public boolean removeAll(Integer[] ids) {
        return dao.removeAll(ids);
    }

    @Override
    public boolean update(T bean) {
        return dao.update(bean);
    }

    @Override
    public List<T> list() {
        return dao.list();
    }

    @Override
    public Integer count() {
        return dao.count();
    }

}

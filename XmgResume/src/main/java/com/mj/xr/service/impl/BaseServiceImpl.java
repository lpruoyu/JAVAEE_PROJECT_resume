package com.mj.xr.service.impl;

import com.mj.xr.dao.BaseDao;
import com.mj.xr.service.BaseService;

import java.util.List;

@SuppressWarnings("unchecked")
public abstract class BaseServiceImpl<T> implements BaseService<T> {
    protected BaseDao<T> dao = newDao();
    protected BaseDao<T> newDao() {
        // com.mj.xr.service.impl.WebsiteServiceImpl
        // com.mj.xr.dao.impl.WebsiteDaoImpl
        try {
            String clsName = getClass().getName()
                    .replace(".service.", ".dao.")
                    .replace("Service", "Dao");
            return (BaseDao<T>) Class.forName(clsName).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除
     */
    public boolean remove(Integer id) {
        return dao.remove(id);
    }

    @Override
    public boolean remove(List<Integer> ids) {
        return dao.remove(ids);
    }

    /**
     * 添加或更新
     */
    public boolean save(T bean) {
        return dao.save(bean);
    }

    /**
     * 获取单个对象
     */
    public T get(Integer id) {
        return dao.get(id);
    }

    /**
     * 获取多个对象
     */
    public List<T> list() {
        return dao.list();
    }

    /**
     * 获取统计值
     */
    public int count() {
        return dao.count();
    }
}

package programmer.lp.resume.service.impl;

import programmer.lp.resume.base.BaseServiceImpl;
import programmer.lp.resume.bean.Award;
import programmer.lp.resume.dao.impl.AwardDaoImpl;
import programmer.lp.resume.service.AwardService;
import programmer.lp.resume.util.Uploads;

import java.util.List;

public class AwardServiceImpl extends BaseServiceImpl<Award> implements AwardService {

    private String localFileBasePath;

    public void setLocalFileBasePath(String path) {
        localFileBasePath = path;
    }
//
//    @Override
//    protected BaseDao<Award> dao() {
//        return new AwardDaoImpl();
//    }

    @Override
    public boolean removeAll(Integer[] ids) {
        AwardDaoImpl impl = (AwardDaoImpl) dao();
        final List<String> list = impl.list(ids);
        for (String s : list) {
            Uploads.removeImage(s, localFileBasePath);
        }
        return impl.removeAll(ids);
    }

}

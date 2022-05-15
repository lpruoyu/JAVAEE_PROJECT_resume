package programmer.lp.resume.service.impl;

import programmer.lp.resume.base.BaseServiceImpl;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.dao.impl.CompanyDaoImpl;
import programmer.lp.resume.service.CompanyService;
import programmer.lp.resume.util.Uploads;

import java.util.List;

public class CompanyServiceImpl extends BaseServiceImpl<Company> implements CompanyService {

    private String localFileBasePath;

    public void setLocalFileBasePath(String path) {
        localFileBasePath = path;
    }

//    @Override
//    protected BaseDao<Award> dao() {
//        return new AwardDaoImpl();
//    }

    public boolean removeAll(Integer[] ids) {
        CompanyDaoImpl impl = (CompanyDaoImpl) dao();
        if (impl.removeAll(ids)) {
            final List<String> list = impl.list(ids);
            for (String s : list) {
                Uploads.removeImage(s, localFileBasePath);
            }
            return true;
        }
        return false;
    }

}

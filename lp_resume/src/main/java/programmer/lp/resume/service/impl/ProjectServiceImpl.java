package programmer.lp.resume.service.impl;

import programmer.lp.resume.base.BaseServiceImpl;
import programmer.lp.resume.bean.Project;
import programmer.lp.resume.dao.impl.CompanyDaoImpl;
import programmer.lp.resume.dao.impl.ProjectDaoImpl;
import programmer.lp.resume.service.ProjectService;
import programmer.lp.resume.util.Uploads;

import java.util.List;

public class ProjectServiceImpl extends BaseServiceImpl<Project> implements ProjectService {

    private String localFileBasePath;

    public void setLocalFileBasePath(String path) {
        localFileBasePath = path;
    }

    public boolean removeAll(Integer[] ids) {
        ProjectDaoImpl impl = (ProjectDaoImpl) dao();
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

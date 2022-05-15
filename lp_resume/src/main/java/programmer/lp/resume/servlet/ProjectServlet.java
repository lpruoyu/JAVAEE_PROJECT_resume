package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.bean.Project;
import programmer.lp.resume.service.CompanyService;
import programmer.lp.resume.service.UserService;
import programmer.lp.resume.service.WebsiteService;
import programmer.lp.resume.service.impl.CompanyServiceImpl;
import programmer.lp.resume.service.impl.ProjectServiceImpl;
import programmer.lp.resume.service.impl.UserServiceImpl;
import programmer.lp.resume.service.impl.WebsiteServiceImpl;
import programmer.lp.resume.util.Uploads;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet("/project/*")
public class ProjectServlet extends BaseServlet<Project> {

    private final CompanyService companyService = new CompanyServiceImpl();

    private WebsiteService websiteService = new WebsiteServiceImpl();
    private UserService userService = new UserServiceImpl();

    public void front(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("projects", service.list());
        req.setAttribute("user", userService.list().get(0));
        req.setAttribute("website", websiteService.list().get(0));
        forward(req, resp, "front/project.jsp");
    }

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("projects", service.list());
            req.setAttribute("companies", companyService.list());
            forward(req, resp, "admin/project.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        String finalImage = null;
        try {
            final Map<String, String> params = Uploads.parseParam(req);
            finalImage = params.get("finalImage");
            Project project = new Project();
            Company company = new Company();
            BeanUtils.populate(project, params);
            company.setId(Integer.valueOf(params.get("companyId")));
            project.setCompany(company);

            String oldImage = null;
            if (finalImage != null) {
                oldImage = project.getImage();
                project.setImage(finalImage);
            }

            try {
                if (service.save(project)) {
                    if (oldImage != null && !"".equals(oldImage)) {
                        Uploads.removeImage(oldImage, req.getServletContext());
                    }
                    redirect(req, resp, "/project/admin");
                } else {
                    if (finalImage != null) {
                        Uploads.removeImage(finalImage, req.getServletContext());
                    }
                    forwardError("项目经验保存失败", req, resp);
                }
            } catch (Exception e) {
                if (finalImage != null) {
                    Uploads.removeImage(finalImage, req.getServletContext());
                }
                forwardError(e, req, resp);
            }
        } catch (Exception e) {
            if (finalImage != null) {
                Uploads.removeImage(finalImage, req.getServletContext());
            }
            forwardError(e, req, resp);
        }
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) {

        ProjectServiceImpl projectService = (ProjectServiceImpl) service;
        projectService.setLocalFileBasePath(req.getServletContext().getRealPath("/"));

        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "project/admin");
            } else {
                forwardError("项目经验删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

}

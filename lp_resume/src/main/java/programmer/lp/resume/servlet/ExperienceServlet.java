package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Company;
import programmer.lp.resume.bean.Experience;
import programmer.lp.resume.service.CompanyService;
import programmer.lp.resume.service.UserService;
import programmer.lp.resume.service.WebsiteService;
import programmer.lp.resume.service.impl.CompanyServiceImpl;
import programmer.lp.resume.service.impl.UserServiceImpl;
import programmer.lp.resume.service.impl.WebsiteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/experience/*")
public class ExperienceServlet extends BaseServlet<Experience> {

    private final CompanyService companyService = new CompanyServiceImpl();

    private WebsiteService websiteService = new WebsiteServiceImpl();
    private UserService userService = new UserServiceImpl();

    public void front(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("experiences", service.list());
        req.setAttribute("user", userService.list().get(0));
        req.setAttribute("website", websiteService.list().get(0));
        forward(req, resp, "front/experience.jsp");
    }

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("experiences", service.list());
            req.setAttribute("companies", companyService.list());
            forward(req, resp, "admin/experience.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Experience experience = new Experience();
            BeanUtils.populate(experience, req.getParameterMap());
            Company company = new Company();
            company.setId(Integer.valueOf(req.getParameter("companyId")));
            experience.setCompany(company);

            if (service.save(experience)) {
                redirect(req, resp, "/experience/admin");
            } else {
                forwardError("工作经验保存失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "experience/admin");
            } else {
                forwardError("工作经验删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

}

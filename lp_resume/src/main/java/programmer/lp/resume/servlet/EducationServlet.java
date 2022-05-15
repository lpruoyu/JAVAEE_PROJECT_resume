package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Education;
import programmer.lp.resume.service.EducationService;
import programmer.lp.resume.service.UserService;
import programmer.lp.resume.service.WebsiteService;
import programmer.lp.resume.service.impl.EducationServiceImpl;
import programmer.lp.resume.service.impl.UserServiceImpl;
import programmer.lp.resume.service.impl.WebsiteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/education/*")
public class EducationServlet extends BaseServlet<Education> {

    private WebsiteService websiteService = new WebsiteServiceImpl();
    private UserService userService = new UserServiceImpl();

    public void front(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("educations", service.list());
        req.setAttribute("user", userService.list().get(0));
        req.setAttribute("website", websiteService.list().get(0));
        forward(req, resp, "front/education.jsp");
    }

    private final EducationService service = new EducationServiceImpl();

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("educations", service.list());
            forward(req, resp, "admin/education.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Education education = new Education();
            BeanUtils.populate(education, req.getParameterMap());
            if (service.save(education)) {
                redirect(req, resp, "education/admin");
            } else {
                forwardError("教育信息保存失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    // removeAll -> remove
    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "education/admin");
            } else {
                forwardError("教育信息删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

//    public void remove(HttpServletRequest req, HttpServletResponse resp) {
//        try {
//            Integer id = Integer.valueOf(req.getParameter("id"));
//            if (dao.remove(id)) {
//                resp.sendRedirect(req.getContextPath() + "/education/admin");
//            } else {
//                forwardError("教育信息删除失败", req, resp);
//            }
//        } catch (Exception e) {
//            forwardError("教育信息删除失败", req, resp);
//        }
//    }

}

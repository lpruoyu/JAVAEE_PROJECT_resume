package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.#name#;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/#name#/*")
public class #name#Servlet extends BaseServlet<#name#> {

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("#name#", service.list());
            forward(req, resp, "admin/#name#.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            #name# #name# = new #name#();
            BeanUtils.populate(#name#, req.getParameterMap());

            if (service.save(#name#)) {
                redirect(req, resp, "/#name#/admin");
            } else {
                forwardError("#name#保存失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void remove(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (service.removeAll(intIds(req.getParameterValues("id")))) {
                redirect(req, resp, "#name#/admin");
            } else {
                forwardError("#name#删除失败", req, resp);
            }
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

}

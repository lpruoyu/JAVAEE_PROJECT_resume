package programmer.lp.resume.servlet;

import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.Website;
import programmer.lp.resume.service.WebsiteService;
import programmer.lp.resume.service.impl.WebsiteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/website/*")
public class WebsiteServlet extends BaseServlet<Website> {

    private final WebsiteService service = new WebsiteServiceImpl();

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final List<Website> websites = service.list();
            Website website = null;
            if (null != websites && !websites.isEmpty()) {
                website = websites.get(0);
            }
            req.setAttribute("website", website);
            forward(req, resp, "admin/website.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Website website = new Website();
            BeanUtils.populate(website, req.getParameterMap());
            if (service.save(website)) {
                redirect(req, resp, "website/admin");
            } else {
                forwardError("网站信息保存失败", req, resp);
            }

//            if (null == website.getId()) { // 插入
//                // 如果用户直接在浏览器输入save的话，此时id为null，也会插入一条数据，如何解决？
//                if (dao.insert(website)) { // 成功
//                    resp.sendRedirect(req.getContextPath() + "/website/admin");
//                } else { // 失败
//                    forwardError("数据保存失败", req, resp);
//                }
//            } else { // 更新
//                if (dao.update(website)) { // 成功
//                    resp.sendRedirect(req.getContextPath() + "/website/admin");
//                } else { // 失败
//                    forwardError("数据保存失败", req, resp);
//                }
//            }

            // 解决方法：直接更新数据（在用户注册时就插入该数据）
//            if (dao.update(website)) { // 成功
//                resp.sendRedirect(req.getContextPath() + "/website/admin");
//            } else { // 失败
//                forwardError("数据保存失败", req, resp);
//            }

        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

}

package com.mj.xr.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mj.xr.bean.Contact;
import com.mj.xr.bean.Contact;
import com.mj.xr.bean.ContactListParam;
import com.mj.xr.service.ContactService;
import com.mj.xr.service.UserService;
import com.mj.xr.service.WebsiteService;
import com.mj.xr.service.impl.UserServiceImpl;
import com.mj.xr.service.impl.WebsiteServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/contact/*")
public class ContactServlet extends BaseServlet<Contact> {
    private UserService userService = new UserServiceImpl();
    private WebsiteService websiteService = new WebsiteServiceImpl();

    public void front(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("user", userService.list().get(0));
        request.setAttribute("footer", websiteService.list().get(0).getFooter());
        // 转发
        forward(request, response, "front/contact.jsp");
    }

    public void admin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ContactListParam param = new ContactListParam();
        BeanUtils.populate(param, request.getParameterMap());

        request.setAttribute("result", ((ContactService) service).list(param));
        forward(request, response, "admin/contact.jsp");
    }

    public void save(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 检查验证码
        String code = (String) request.getSession().getAttribute("code");
        String captcha = request.getParameter("captcha");
        if (!code.equals(captcha)) {
            forwardError(request, response, "验证码错误");
            return;
        }

        Contact contact = new Contact();
        BeanUtils.populate(contact, request.getParameterMap());
        if (service.save(contact)) { // 保存成功
            redirect(request, response, "contact/front");
        } else {
            forwardError(request, response, "留言信息保存失败");
        }
    }

    public void read(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Integer id = Integer.valueOf(request.getParameter("id"));

        Map<String, Object> result = new HashMap<>();
        if (((ContactService) service).read(id)) {
            result.put("success", true);
            result.put("msg", "查看成功");
        } else {
            result.put("success", false);
            result.put("msg", "查看失败");
        }

        response.setContentType("text/json; charset=UTF-8");
        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }
}

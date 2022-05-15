package programmer.lp.resume.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.apache.commons.beanutils.BeanUtils;
import programmer.lp.resume.base.BaseServlet;
import programmer.lp.resume.bean.User;
import programmer.lp.resume.service.AwardService;
import programmer.lp.resume.service.SkillService;
import programmer.lp.resume.service.UserService;
import programmer.lp.resume.service.WebsiteService;
import programmer.lp.resume.service.impl.AwardServiceImpl;
import programmer.lp.resume.service.impl.SkillServiceImpl;
import programmer.lp.resume.service.impl.WebsiteServiceImpl;
import programmer.lp.resume.util.Strings;
import programmer.lp.resume.util.Uploads;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet<User> {

    private WebsiteService websiteService = new WebsiteServiceImpl();
    private AwardService awardService = new AwardServiceImpl();
    private SkillService skillService = new SkillServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // web.xml中转发到这儿，但是浏览器地址栏中的URl没有改变，因此需要在次地特殊处理
        req.setCharacterEncoding("UTF-8");
        String[] split = req.getRequestURI().split("/");
        String methodName = "/" + split[split.length - 1];
        if (methodName.equals(req.getContextPath())) {
            front(req, resp);
        } else {
            super.doPost(req, resp);
        }
    }

    public void front(HttpServletRequest req, HttpServletResponse resp) {
        User user = service.list().get(0);
        req.setAttribute("user", user);
        req.setAttribute("traits", user.getTrait().split(","));
        req.setAttribute("interests", user.getInterests().split(","));
        req.setAttribute("awards", awardService.list());
        req.setAttribute("skills", skillService.list());
        req.setAttribute("website", websiteService.list().get(0));
        forward(req, resp, "front/user.jsp");
    }

    public void updatePassword(HttpServletRequest req, HttpServletResponse resp) {
        final String oldPassword = req.getParameter("oldPassword");
        final String newPassword = req.getParameter("newPassword");

        final User sessionUser = user(req);
        if (sessionUser.getPassword().equals(oldPassword)) {
            sessionUser.setPassword(newPassword);
            if (service.save(sessionUser)) { // 保存成功
                logout(req, resp); // 退出登录
            } else {
                forwardError("密码更新失败", req, resp);
            }
        } else {
            // 旧密码输入错误
            forwardError("旧密码填写错误", req, resp);
        }
    }

    public void password(HttpServletRequest req, HttpServletResponse resp) {
        forward(req, resp, "admin/password.jsp");
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().removeAttribute("user");
        redirect(req, resp, "login.jsp");
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Map<String, Object> map = new HashMap<>();
            String captchaClient = req.getParameter("captcha");
            if (Strings.isNotNull(captchaClient)) {
                // 将用户输入的验证码转化为小写形式
                captchaClient = captchaClient.toLowerCase();
            }
            if (!captchaClient.equals(req.getSession().getAttribute("captcha"))) { // 验证码不正确
                map.put("success", false);
                map.put("message", "验证码不正确");
            } else { // 验证码正确
                User user = new User();
                BeanUtils.populate(user, req.getParameterMap());
                UserService userService = (UserService) service;
                user = userService.login(user);
                if (null != user) { // 登录成功
                    // 将User对象设置到Session中，代表用户已经登录过了
                    req.getSession().setAttribute("user", user);
                    map.put("success", true);
                } else {
                    map.put("success", false);
                    map.put("message", "用户名或密码不正确");
                }
            }
            resp.setContentType("text/json; charset=UTF-8");
            resp.getWriter().write(new ObjectMapper().writeValueAsString(map));
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void admin(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("user", user(req));
            forward(req, resp, "admin/user.jsp");
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    public void save(HttpServletRequest req, HttpServletResponse resp) {
        String finalImage = null;
        try {
            User user = new User();
            final Map<String, String> params = Uploads.parseParam(req);
            BeanUtils.populate(user, params);
            finalImage = params.get("finalImage");
            User userInSession = user(req);
            user.setEmail(userInSession.getEmail());
            user.setPassword(userInSession.getPassword());
            String oldImage = null;
            if (finalImage != null) { // 用户上传了文件
                oldImage = user.getPhoto();
                user.setPhoto(finalImage);
            }
            if (service.save(user)) { // 保存成功
                if (Strings.isNotNull(oldImage)) { // 删除旧图片
                    Uploads.removeImage(oldImage, req.getServletContext());
                }
                // 更新User对象
                req.getSession().setAttribute("user", user);
                redirect(req, resp, "user/admin");
            } else {
                if (finalImage != null) {
                    Uploads.removeImage(finalImage, req.getServletContext());
                }
                forwardError("User保存失败", req, resp);
            }
        } catch (Exception e) {
            if (finalImage != null) {
                Uploads.removeImage(finalImage, req.getServletContext());
            }
            forwardError(e, req, resp);
        }
    }

    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 创建Katpcha对象
        DefaultKaptcha dk = new DefaultKaptcha();

        // 验证码的配置
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("kaptcha.properties")) {
            Properties properties = new Properties();
            properties.load(is);

            Config config = new Config(properties);
            dk.setConfig(config);
        }

        // 生成验证码字符串
        String code = dk.createText();

        // 将生成的验证码转为小写存入Session中
        request.getSession().setAttribute("captcha", code.toLowerCase());
        // 生成验证码图片
        BufferedImage image = dk.createImage(code);

        // 设置返回数据的格式
        response.setContentType("image/jpeg");

        // 将图片数据写会到客户端
        ImageIO.write(image, "jpg", response.getOutputStream());
    }

    private User user(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("user");
    }

}
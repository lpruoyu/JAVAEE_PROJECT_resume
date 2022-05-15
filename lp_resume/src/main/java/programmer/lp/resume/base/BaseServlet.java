package programmer.lp.resume.base;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public abstract class BaseServlet<T extends BaseBean> extends HttpServlet {

    protected final BaseService<T> service = newService();

    private BaseService<T> newService() {
        // programmer.lp.resume.servlet.AwardServlet
        // programmer.lp.resume.service.impl.AwardServiceImpl
        String name = this.getClass().getName()
                .replace(".servlet.", ".service.impl.")
                .replace("Servlet", "ServiceImpl");
        try {
            return (BaseService<T>) Class.forName(name).newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String[] split = req.getRequestURI().split("/");
            String methodName = split[split.length - 1];
            Method method = getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (Exception e) {
            forwardError(e, req, resp);
        }
    }

    protected void redirect(HttpServletRequest req, HttpServletResponse resp, String path) {
        try {
            resp.sendRedirect(req.getContextPath() + "/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void forward(HttpServletRequest req, HttpServletResponse resp, String path) {
        try {
            req.getRequestDispatcher("/WEB-INF/" + path).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void forwardError(Exception error, HttpServletRequest req, HttpServletResponse resp) {
        try {
            error.printStackTrace();
            // 找到最根本的异常信息
            while (error.getCause() != null) {
                error = (Exception) error.getCause();
            }

            req.setAttribute("error", error);
//            req.getRequestDispatcher("../WEB-INF/error.jsp").forward(req, resp);
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void forwardError(String error, HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("error", error);
            req.getRequestDispatcher("/WEB-INF/error.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void forward404(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // 配置好web.xml中的<error-page/>后，在此处随便重定向至一个不存在的页面，最终都会访问配置好的WEB-INF中的404.jsp
            resp.sendRedirect(req.getContextPath() + "/404");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected Integer[] intIds(String[] ids) {
        final Integer[] arr = new Integer[ids.length];
        for (int i = 0; i < ids.length; i++) {
            arr[i] = Integer.parseInt(ids[i]);
        }
        return arr;
    }

}

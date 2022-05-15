package programmer.lp.resume.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 拦截所有请求
@WebFilter("/*")
public class LoginFilter implements Filter {

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        final HttpServletRequest hreq = (HttpServletRequest) request;
        final HttpServletResponse hresp = (HttpServletResponse) response;

        final String uri = hreq.getRequestURI();

        // 优先将资源放行
        if (uri.contains("/asset/")) {
            chain.doFilter(request, response);
            return;
        }

        // 放行用户留言请求
        if (uri.contains("/contact/save")) {
            chain.doFilter(request, response);
            return;
        }

        // 需要进行登录验证的请求
        if (
                uri.contains("/admin") ||
                        uri.contains("/save") ||
                        uri.contains("/remove") ||
                        uri.contains("/user/password") ||
                        uri.contains("/user/updatePassword")
        ) {
            if (hreq.getSession().getAttribute("user") == null) { // 没登录过
                hresp.sendRedirect(hreq.getContextPath() + "/login.jsp");
            } else { // 登录过
                chain.doFilter(request, response);
            }
        } else { // 不需要进行登录验证的请求
            chain.doFilter(request, response);
        }
    }

}

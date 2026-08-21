package filter;

import model.User;
import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(filterName = "AuthorizationFilter", urlPatterns = {"/*"})
public class AuthorizationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        
        String uri = req.getRequestURI();
        User account = (User) session.getAttribute("account");

        // Bỏ qua trang đăng nhập, đăng xuất và các file tĩnh
        if (uri.endsWith("/login") || uri.endsWith("/logout") || uri.endsWith("/login.jsp") 
            || uri.contains("/css/") || uri.contains("/images/")) {
            chain.doFilter(request, response);
            return;
        }

        // Chặn người dùng chưa đăng nhập
        if (account == null) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        int roleId = account.getRoleId();

        // Chặn vào sai quyền
        if (uri.contains("/admin/") && roleId != 1) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/teacher/") && roleId != 2) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        if (uri.contains("/student/") && roleId != 3) {
            res.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}
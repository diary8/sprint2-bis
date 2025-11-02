package com.myframework;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class FrontServlet implements Filter {

    private RequestDispatcher defaultDispatcher;

    @Override
    public void init(FilterConfig filterConfig) {
        defaultDispatcher = filterConfig.getServletContext().getNamedDispatcher("default");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

     
        String path = req.getRequestURI().substring(req.getContextPath().length());

        boolean resourceExists = req.getServletContext().getResource(path) != null;

         

        if (resourceExists) {
            defaultDispatcher.forward(req, res);
        } else {
            try (var out = res.getWriter()) {
                res.setContentType("text/html;charset=UTF-8");
                out.println("<html><head><title>Not Found</title></head><body>");
                out.println("<h1>Unknown resource</h1>");
                out.println("<p>The requested URL was not found: <strong>" + path + "</strong></p>");
                out.println("</body></html>");
            }
        }
    }

    @Override
    public void destroy() { }
}
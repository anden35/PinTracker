/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pintracker.helperclasses;

/**
 *
 * @author Adam
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"*.xhtml"})
public class PhaseListener implements Filter {

    public PhaseListener() {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        try {

            HttpServletRequest reqt = (HttpServletRequest) request;
            HttpServletResponse resp = (HttpServletResponse) response;
            HttpSession ses = reqt.getSession(false);

            String reqURI = reqt.getRequestURI();
            if (reqURI.indexOf("/login.xhtml") >= 0
                    || (ses != null && ses.getAttribute("username") != null && reqURI.indexOf("/protected/") >= 0)
                    || reqURI.contains("javax.faces.resource")) {
                chain.doFilter(request, response);
                System.out.println("Indexof /login.xhtml >=0 "+ reqURI.indexOf("/login.xhtml"));
                System.out.println("Session not null "+ses);
                System.out.println("Session get username not null "+ses.getAttribute("username"));
                System.out.println("indexOF /protected/ >= 0 "+reqURI.indexOf("/protected/"));
                System.out.println("contains javax.faces.resource" + reqURI.contains("javax.faces.resource"));
            } else {
                resp.sendRedirect(reqt.getContextPath() + "/login.xhtml");
                System.out.println("ContextPath"+reqt.getContextPath());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void destroy() {

    }
}

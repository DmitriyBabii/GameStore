package services.servlets;

import models.SystemUser;
import services.ServiceHibernate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/store"})
public class StoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("game");
        ServiceHibernate.setSearchCriterion(param);
        if(SystemUser.isPresent()){
            String account = SystemUser.getUser().getUsername()
                    + "(" + SystemUser.getUser().getRole().charAt(0)
                    + SystemUser.getUser().getRole().charAt(1) + ")";

            req.setAttribute("account", account);
        }else {
            req.setAttribute("account", "Log in");
        }
        req.getRequestDispatcher("store.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String param = req.getParameter("game");
        ServiceHibernate.setSearchCriterion(param);
        req.getRequestDispatcher("store.jsp").forward(req, resp);
    }
}

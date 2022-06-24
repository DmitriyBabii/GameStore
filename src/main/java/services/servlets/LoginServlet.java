package services.servlets;

import models.SystemUser;
import models.figures.AuthorizedUser;
import services.CartService;
import services.ServiceHibernate;
import services.entity.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String exit = req.getParameter("exit");

        if(exit!=null){
            SystemUser.exit();
            resp.sendRedirect("/game-store/login");
            return;
        }

        if (SystemUser.isPresent()) {
            String account = SystemUser.getUser().getUsername()
                    + "(" + SystemUser.getUser().getRole().charAt(0)
                    + SystemUser.getUser().getRole().charAt(1) + ")";

            req.setAttribute("account", account);
        } else {
            req.setAttribute("account", "Log in");
        }
        req.setAttribute("data", "Log in");
        req.setAttribute("color", "black");
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserService();
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        @SuppressWarnings("unchecked")
        Optional<AuthorizedUser> user = (Optional<AuthorizedUser>) us.select(
                ServiceHibernate.getUserLoginCriterion(username, password)
        ).stream().findFirst();

        if (user.isPresent()) {
            SystemUser.init(user.get(), req.getSession());
            CartService.init(SystemUser.getUser());
            doGet(req, resp);
        } else {
            SystemUser.exit();
            CartService.exit();
            req.setAttribute("account", "Log in");
            req.setAttribute("color", "red");
            req.setAttribute("data", "Incorrect data");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}

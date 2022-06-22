package services.servlets;

import models.SystemUser;
import services.CartService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String param = req.getParameter("delete");
        if (param != null && param.equals("all")) {
            CartService.clear();
            resp.sendRedirect("/game-store/cart");
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
        req.getRequestDispatcher("cart.jsp").forward(req, resp);
    }
}

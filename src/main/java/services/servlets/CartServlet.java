package services.servlets;

import models.Order;
import models.SystemUser;
import models.enums.Role;
import models.figures.Client;
import services.CartService;
import services.entity.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/cart"})
public class CartServlet extends HttpServlet {
    private static final OrderService os = new OrderService();

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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (CartService.getCart().getProductList().size() > 0
                && SystemUser.getUser().getElementRole() == Role.CLIENT) {
            os.save(new Order((Client) SystemUser.getUser(), CartService.getCart().getProductList()));
            CartService.clear();
        }
        doGet(req, resp);
    }
}

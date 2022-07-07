package services.servlets;

import intarfaces.EntityEnum;
import models.Order;
import models.SystemUser;
import models.enums.Role;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import services.entity.EntityService;
import services.entity.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@WebServlet(urlPatterns = {"/task"})
public class TaskServlet  extends HttpServlet {
    private static final OrderService os = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idOrder = req.getParameter("order");
        String accept = req.getParameter("accept");
        String cancel = req.getParameter("cancel");

        req.setAttribute("task", EntityService.nativeQuery("SELECT \n" +
                "    COUNT(*) AS count_of_orders, name\n" +
                "FROM\n" +
                "    game_shop.user\n" +
                "        INNER JOIN\n" +
                "    game_shop.order ON game_shop.user.id_user = game_shop.order.id_user_courier_fk\n" +
                "GROUP BY name\n" +
                "ORDER BY count_of_orders DESC limit 5;"));

        if (SystemUser.isPresent()) {
            String account = SystemUser.getUser().getUsername()
                    + "(" + SystemUser.getUser().getRole().charAt(0)
                    + SystemUser.getUser().getRole().charAt(1) + ")";

            req.setAttribute("account", account);
        } else {
            req.setAttribute("orders", null);
            req.setAttribute("account", "Log in");
        }

        if (SystemUser.isPresent()) {
            if (idOrder != null) {
                req.setAttribute("idOrder", idOrder);
                req.setAttribute("productsInOrder", os.getProducts(idOrder));
                req.setAttribute("orderEntity", os.getOrder(idOrder));
                req.getRequestDispatcher("order.jsp").forward(req, resp);
            }
        }

        req.getRequestDispatcher("task.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}

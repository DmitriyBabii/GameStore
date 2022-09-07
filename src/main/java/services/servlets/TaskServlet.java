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
                "    COUNT(*) AS count, SUM(book_shop.product.price) AS sum_price, book_shop.user.name\n" +
                "FROM\n" +
                "    book_shop.order\n" +
                "        INNER JOIN\n" +
                "    book_shop.product_in_order ON book_shop.order.id_order = book_shop.product_in_order.id_order_fk\n" +
                "        INNER JOIN\n" +
                "    book_shop.product ON book_shop.product_in_order.id_product_fk = book_shop.product.id_product\n" +
                "        INNER JOIN\n" +
                "    book_shop.user ON book_shop.order.id_user_client_fk = book_shop.user.id_user\n" +
                "WHERE\n" +
                "    book_shop.order.end_date_courier IS NOT NULL\n" +
                "GROUP BY name\n" +
                "ORDER BY sum_price DESC\n" +
                "LIMIT 5;"));

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

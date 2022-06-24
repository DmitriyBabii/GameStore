package services.servlets;

import models.Order;
import models.SystemUser;
import models.enums.Role;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
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

@WebServlet(urlPatterns = {"/order"})
public class OrderServlet extends HttpServlet {
    private static final OrderService os = new OrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idOrder = req.getParameter("order");
        String accept = req.getParameter("accept");

        if (SystemUser.isPresent()) {
            String account = SystemUser.getUser().getUsername()
                    + "(" + SystemUser.getUser().getRole().charAt(0)
                    + SystemUser.getUser().getRole().charAt(1) + ")";

            setButtonSize(req);
            req.setAttribute("orders", getOrders());
            req.setAttribute("account", account);
        } else {
            req.setAttribute("orders", null);
            req.setAttribute("account", "Log in");
        }

        if (SystemUser.isPresent()) {
            if (idOrder != null) {
                System.out.println(os.getProducts(idOrder));
                req.setAttribute("idOrder", idOrder);
                req.setAttribute("productsInOrder", os.getProducts(idOrder));
                req.setAttribute("orderEntity", os.getOrder(idOrder));
                req.getRequestDispatcher("order.jsp").forward(req, resp);
            }
            if (accept != null) {
                setAccept(accept, resp);
                return;
            }
        }

        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private List<Order> getOrders() {
        List<Order> orders;
        switch (SystemUser.getUser().getElementRole()) {
            case CLIENT: {
                orders = os.getOrdersForUser(SystemUser.getUser().getId());
                break;
            }
            case MANAGER: {
                orders = os.getOrdersForManager();
                break;
            }
            case STOREKEEPER: {
                orders = os.getOrdersForStorekeeper();
                break;
            }
            case COURIER: {
                orders = os.getOrdersForCourier();
                break;
            }
            default:
                orders = null;
        }
        return orders;
    }

    private void setButtonSize(HttpServletRequest req) {
        if (SystemUser.getUser().getElementRole() == Role.CLIENT) {
            req.setAttribute("button", "100%");
        } else {
            req.setAttribute("button", "49%");
        }
    }

    private void setAccept(String accept, HttpServletResponse resp) throws IOException {
        Order order = os.getOrder(accept);
        if (order == null) return;
        switch (SystemUser.getUser().getElementRole()) {
            case MANAGER: {
                order.setManager((Manager) SystemUser.getUser());
                order.setEndDateManager(Date.valueOf(LocalDate.now()));
                break;
            }
            case STOREKEEPER: {
                order.setStorekeeper((Storekeeper) SystemUser.getUser());
                order.setEndDateStorekeeper(Date.valueOf(LocalDate.now()));
                break;
            }
            case COURIER: {
                order.setCourier((Courier) SystemUser.getUser());
                order.setEndDateCourier(Date.valueOf(LocalDate.now()));
                break;
            }
            default:
                return;
        }
        os.update(order);
        resp.sendRedirect("/game-store/order");
    }
}

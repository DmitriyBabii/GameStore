package services.servlets;

import models.Product;
import models.Rating;
import models.SystemUser;
import services.CartService;
import services.ServiceHibernate;
import services.entity.ProductService;
import services.entity.RatingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@WebServlet(urlPatterns = {"/product/*"})
public class ProductServlet extends HttpServlet {
    private static final ProductService productService = new ProductService();
    private static final RatingService ratingService = new RatingService();
    private Product product;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idProduct = req.getParameter("game");
        String review = req.getParameter("review");

        if (review != null) {
            System.out.println(review);
            if (SystemUser.isPresent() && !ratingService.isThere(SystemUser.getUser(), product)) {
                ratingService.save(new Rating(SystemUser.getUser(), product, review, Date.valueOf(LocalDate.now())));
            } else if (SystemUser.isPresent()) {
                ratingService.update(SystemUser.getUser(), product, review);
            }
            resp.sendRedirect("/game-store/product?game=" + product.getId());
            return;
        }

        product = productService.getProduct(idProduct);
        req.setAttribute("product", product);

        req.setAttribute("review", ratingService.getProductReview(idProduct));

        if (CartService.isThere(product)) {
            req.setAttribute("color", "#e8ffe1");
            req.setAttribute("text", "black");
        } else {
            req.setAttribute("color", "#009800");
            req.setAttribute("text", "white");
        }
        if (SystemUser.isPresent()) {
            String account = SystemUser.getUser().getUsername()
                    + "(" + SystemUser.getUser().getRole().charAt(0)
                    + SystemUser.getUser().getRole().charAt(1) + ")";
            req.setAttribute("account", account);
        } else {
            req.setAttribute("account", "Log in");
        }
        req.getRequestDispatcher("product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!CartService.isThere(product)) {
            CartService.addProduct(product);
            System.out.println(CartService.getCart());
        }
        doGet(req, resp);
    }
}

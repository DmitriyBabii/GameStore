import intarfaces.Entity;
import models.*;
import models.enums.*;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import services.CriterionService;
import services.ServiceHibernate;
import services.entity.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ServiceHibernate.start();
        Product product = new Product("Witcher 3", Date.valueOf(LocalDate.now()), "dadsasd", AgeLimit._18, 5000.0);
        Product product1 = new Product("Witcher 2", Date.valueOf(LocalDate.now()), "dadsasd", AgeLimit._18, 3.0);
        Product product2 = new Product("Witcher 1", Date.valueOf(LocalDate.now()), "dadsasd", AgeLimit._18, 2.2);

        Storage storage = new Storage(product, 10);

        WaitingProduct waitingProduct = new WaitingProduct(product1, 1);

        ReservedProduct reservedProduct = new ReservedProduct(product1, 1);

        Client client = new Client("Dima", "Babii", "Mitar", "Babii0706",
                "+380504021867", "v.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Rating rating = new Rating(client, product, "dadsasd", Date.valueOf(LocalDate.now()));
        Manager manager = new Manager("Manager", "Babii", "Mitar1", "Babii0706",
                "+380504121867", "1.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Storekeeper storekeeper = new Storekeeper("Storekeeper", "Babii", "Mitar2", "Babii0706",
                "+380504221867", "2.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Courier courier = new Courier("Courier", "Babii", "Mitar3", "Babii0706",
                "+380504321867", "3.babiy75@gmail.com", Date.valueOf(LocalDate.now()));

        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product1);
        Order order = new Order(manager, storekeeper, courier, client,
                productList, 2200.0, Date.valueOf(LocalDate.now()));


        Entity[] entities = {
                client, manager, storekeeper, courier
        };


        ProductService productService = new ProductService();
        StorageService storageService = new StorageService();
        UserService userService = new UserService();
        RatingService ratingService = new RatingService();
        OrderService orderService = new OrderService();
        WaitingProductService waitingProductService = new WaitingProductService();
        ReservedProductService reservedProductService = new ReservedProductService();

        productService.save(product, product1, product2);
        storageService.save(storage);
        userService.save(entities);
        ratingService.save(rating);
        orderService.save(order);
        waitingProductService.save(waitingProduct);
        reservedProductService.save(reservedProduct);

        product.setPrice(0.1);
        storage.setQuantity(1);
        client.setName("1");
        manager.setName("1");
        storekeeper.setName("1");
        courier.setName("1");
        rating.setReview("1");
        order.setEndDateStorekeeper(Date.valueOf(LocalDate.now()));
        order.addProduct(product2);
        waitingProduct.setQuantity(2);
        reservedProduct.setQuantity(2);

        productService.update(product);
        storageService.update(storage);
        userService.update(client);
        userService.update(manager);
        userService.update(storekeeper);
        userService.update(courier);
        ratingService.update(rating);
        orderService.update(order);
        waitingProductService.update(waitingProduct);
        reservedProductService.update(reservedProduct);


        CriterionService criterionProduct = new CriterionService();
        criterionProduct.addCriterion(EProduct.name, "Witcher 3");
        criterionProduct.addCriterion(EProduct.price, Operator.NOT_EQUALS, 2.2);
        System.out.println(productService.select(criterionProduct.getCriterionList()));

        CriterionService criterionStorage = new CriterionService();
        criterionStorage.addCriterion(EStorage.quantity, 1);
        System.out.println(storageService.select(criterionStorage.getCriterionList()));

        CriterionService criterionUser = new CriterionService();
        criterionUser.addCriterion(EUser.last_name, "Babii");
        System.out.println(userService.select(criterionUser.getCriterionList()));

        CriterionService criterionRating = new CriterionService();
        criterionRating.addCriterion(ERating.review, "1");
        System.out.println(ratingService.select(criterionRating.getCriterionList()));

        CriterionService criterionOrder = new CriterionService();
        criterionOrder.addCriterion(EOrder.price, 2200);
        criterionOrder.addCriterion(EOrder.end_date_storekeeper, Operator.NOT_NULL, null);
        System.out.println(orderService.select(criterionOrder.getCriterionList()));

        CriterionService criterionWaiting = new CriterionService();
        criterionWaiting.addCriterion(EWaitingProduct.quantity, 2);
        System.out.println(waitingProductService.select(criterionWaiting.getCriterionList()));

        CriterionService criterionReserved = new CriterionService();
        criterionReserved.addCriterion(EReservedProduct.quantity, 2);
        System.out.println(reservedProductService.select(criterionReserved.getCriterionList()));

        storageService.delete(storage);
        orderService.delete(order);
        ratingService.delete(rating);
        productService.delete(product);
        userService.delete(entities);
        waitingProductService.delete(waitingProduct);
        reservedProductService.delete(reservedProduct);
    }
}

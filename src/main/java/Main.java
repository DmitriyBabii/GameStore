import intarfaces.Entity;
import models.*;
import models.enums.AgeLimit;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;
import services.ServiceHibernate;
import services.entity.*;

import java.sql.Date;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        ServiceHibernate.start();
        Text text = new Text("dadsasd");
        Product product = new Product("Witcher 3", Date.valueOf(LocalDate.now()), text, AgeLimit._18, 5000.0);
        Storage storage = new Storage(product, 10);
        Client client = new Client("Dima", "Babii", "Mitar", "Babii0706",
                "+380504021867", "v.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Rating rating = new Rating(client, product, text, Date.valueOf(LocalDate.now()));
        Manager manager = new Manager("Manager", "Babii", "Mitar1", "Babii0706",
                "+380504121867", "1.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Storekeeper storekeeper = new Storekeeper("Storekeeper", "Babii", "Mitar2", "Babii0706",
                "+380504221867", "2.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Courier courier = new Courier("Courier", "Babii", "Mitar3", "Babii0706",
                "+380504321867", "3.babiy75@gmail.com", Date.valueOf(LocalDate.now()));
        Order order = new Order(manager, storekeeper, courier, client,
                product, 2200.0, Date.valueOf(LocalDate.now()));


        Entity[] entities = {
                client, manager, storekeeper, courier
        };


        ProductService productService = new ProductService();
        StorageService storageService = new StorageService();
        UserService userService = new UserService();
        RatingService ratingService = new RatingService();
        OrderService orderService = new OrderService();

        productService.save(product);
        storageService.save(storage);
        userService.save(entities);
        ratingService.save(rating);
        orderService.save(order);

        product.setPrice(1.1);
        storage.setQuantity(1);
        client.setName("1");
        manager.setName("1");
        storekeeper.setName("1");
        courier.setName("1");
        rating.setReview(new Text("1"));
        order.setEndDateStorekeeper(Date.valueOf(LocalDate.now()));

        productService.update(product);
        storageService.update(storage);
        userService.update(client);
        userService.update(manager);
        userService.update(storekeeper);
        userService.update(courier);
        ratingService.update(rating);
        orderService.update(order);
    }
}

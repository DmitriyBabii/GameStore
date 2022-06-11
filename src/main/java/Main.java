import models.*;
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
        Product product = new Product("das", Date.valueOf(LocalDate.now()), text, 18, 5000);
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

        new ProductService().save(product);
        new StorageService().save(storage);
        UserService userService = new UserService();
        userService.save(client);
        userService.save(manager);
        userService.save(storekeeper);
        userService.save(courier);
        new RatingService().save(rating);
        new OrderService().save(order);
    }
}

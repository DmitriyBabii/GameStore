package models;

import lombok.Value;
import models.figures.Client;
import models.figures.Courier;
import models.figures.Manager;
import models.figures.Storekeeper;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Value
public class Order {
    Manager manager;
    Storekeeper storekeeper;
    Courier courier;
    Client client;
    List<Product> product;
    int price;
    Date startOrder;
    Date endOrder;

    public Order(Manager manager, Storekeeper storekeeper, Courier courier,
                 Client client, List<Product> product, int price) {
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.product = product;
        this.client = client;
        this.price = price;
        this.startOrder = Date.valueOf(LocalDate.now());
        this.endOrder = null;
    }
}

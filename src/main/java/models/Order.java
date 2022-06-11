package models;

import lombok.Value;
import models.figures.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.UUID;

@Value
public class Order {
    String id = UUID.randomUUID().toString();
    Manager manager;
    Storekeeper storekeeper;
    Courier courier;
    Client client;
    Product product;
    double price;
    Date startOrder;
    Date endDateStorekeeper;
    Date endDateCourier;

    public Order(Manager manager, Storekeeper storekeeper, Courier courier,
                 Client client, Product product, double price) {
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.product = product;
        this.client = client;
        this.price = price;
        this.startOrder = Date.valueOf(LocalDate.now());
        this.endDateStorekeeper = null;
        this.endDateCourier = null;
    }
}

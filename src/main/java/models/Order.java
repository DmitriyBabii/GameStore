package models;

import intarfaces.Entity;
import lombok.Getter;
import lombok.Setter;
import models.figures.*;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
public class Order implements Entity {
    private final String id;
    private final Manager manager;
    private final Storekeeper storekeeper;
    private final Courier courier;
    private final Client client;
    private final Product product;
    private final Double price;
    private final Date startOrder;
    private Date endDateStorekeeper;
    private Date endDateCourier;

    public Order(Manager manager, Storekeeper storekeeper, Courier courier, Client client, Product product, Double price, Date startOrder) {
        this.id = UUID.randomUUID().toString();
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.client = client;
        this.product = product;
        this.price = price;
        this.startOrder = startOrder;
        this.endDateStorekeeper = null;
        this.endDateCourier = null;
    }

    public Order(String id, Manager manager, Storekeeper storekeeper, Courier courier, Client client, Product product, Double price, Date startOrder) {
        this.id = id;
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.client = client;
        this.product = product;
        this.price = price;
        this.startOrder = startOrder;
        this.endDateStorekeeper = null;
        this.endDateCourier = null;
    }
}

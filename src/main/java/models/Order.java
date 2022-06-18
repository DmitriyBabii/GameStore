package models;

import intarfaces.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import models.figures.*;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Order implements Entity {
    private final String id;
    private final Manager manager;
    private final Storekeeper storekeeper;
    private final Courier courier;
    private final Client client;
    @Setter(AccessLevel.NONE)
    private List<Product> products;
    private final Double price;
    private final Date startOrder;
    private Date endDateManager;
    private Date endDateStorekeeper;
    private Date endDateCourier;

    public Order(Manager manager, Storekeeper storekeeper, Courier courier, Client client,
                 List<Product> products, Double price, Date startOrder) {
        this.id = UUID.randomUUID().toString();
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.client = client;
        this.products = products;
        this.price = price;
        this.startOrder = startOrder;
        this.endDateStorekeeper = null;
        this.endDateCourier = null;
    }

    /**
     * Need for builder
     */
    @SuppressWarnings("unused")
    public Order(String id, Manager manager, Storekeeper storekeeper, Courier courier,
                 Client client, List<Product> products, Double price, Date startOrder,
                 Date endDateManager, Date endDateStorekeeper, Date endDateCourier) {
        this.id = id;
        this.manager = manager;
        this.storekeeper = storekeeper;
        this.courier = courier;
        this.client = client;
        this.products = products;
        this.price = price;
        this.startOrder = startOrder;
        this.endDateManager = endDateManager;
        this.endDateStorekeeper = endDateStorekeeper;
        this.endDateCourier = endDateCourier;
    }

    public void addProduct(Product... product) {
        Collections.addAll(products, product);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", manager=" + manager +
                ", storekeeper=" + storekeeper +
                ", courier=" + courier +
                ", client=" + client +
                ", products=" + products +
                ", price=" + price +
                ", startOrder=" + startOrder +
                ", endDateManager=" + endDateManager +
                ", endDateStorekeeper=" + endDateStorekeeper +
                ", endDateCourier=" + endDateCourier +
                '}';
    }
}

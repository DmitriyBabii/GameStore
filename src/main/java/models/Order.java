package models;

import intarfaces.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import models.figures.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Order implements Entity {
    private final String id;
    private Manager manager;
    private Storekeeper storekeeper;
    private Courier courier;
    private final Client client;
    @Setter(AccessLevel.NONE)
    private List<Product> products;
    private final Double price;
    private final Date startOrder;
    private Date endDateManager;
    private Date endDateStorekeeper;
    private Date endDateCourier;
    private Boolean cancel;


    public Order(Client client, List<Product> products) {
        this.id = UUID.randomUUID().toString();
        this.client = client;
        this.products = products;
        Double sum = 0.0;
        for (Product p : products) {
            sum += p.getPrice();
        }
        this.price = sum;
        this.startOrder = Date.valueOf(LocalDate.now());
        this.cancel = false;
    }

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
        this.cancel = false;
    }

    /**
     * Need for builder
     */
    @SuppressWarnings("unused")
    public Order(String id, Manager manager, Storekeeper storekeeper, Courier courier,
                 Client client, List<Product> products, Double price, Date startOrder,
                 Date endDateManager, Date endDateStorekeeper, Date endDateCourier, Boolean cancel) {
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
        this.cancel = cancel;
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

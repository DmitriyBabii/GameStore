package models;

import lombok.AllArgsConstructor;
import lombok.Value;
import models.figures.Client;

import java.sql.Date;
import java.util.UUID;

@Value
@AllArgsConstructor
public class Rating {
    String id = UUID.randomUUID().toString();
    Client client;
    Product product;
    Text review;
    Date reviewDate;
}

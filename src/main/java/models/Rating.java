package models;

import lombok.AllArgsConstructor;
import lombok.Value;
import models.figures.Client;

import java.sql.Date;

@Value
@AllArgsConstructor
public class Rating {
    Client client;
    Product product;
    Text review;
    Date reviewDate;
}

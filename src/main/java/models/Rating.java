package models;

import intarfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.Value;
import models.figures.Client;

import java.sql.Date;

@Value
@AllArgsConstructor
public class Rating implements Entity {
    Client client;
    Product product;
    Text review;
    Date reviewDate;

    public String getReview() {
        return review.toString();
    }
}

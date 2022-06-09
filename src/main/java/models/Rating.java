package models;

import lombok.AllArgsConstructor;
import lombok.Value;
import models.figures.AuthorizedClient;

import java.sql.Date;

@Value
@AllArgsConstructor
public class Rating {
    AuthorizedClient client;
    Product product;
    Text review;
    Date reviewDate;
}

package models;

import intarfaces.Entity;
import lombok.*;
import models.enums.AgeLimit;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
public class Product implements Entity {
    private final String id;
    private final String name;
    private final Date dateOfRelease;
    private Text description;
    private AgeLimit ageLimit;
    private Double price;

    public Product(String name, Date dateOfRelease, Text description, AgeLimit ageLimit, Double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.description = description;
        this.ageLimit = ageLimit;
        this.price = price;
    }

    public String getDescription() {
        return description.toString();
    }

    public Integer getAgeLimit() {
        return ageLimit.getValue();
    }
}

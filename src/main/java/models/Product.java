package models;

import intarfaces.Entity;
import lombok.*;
import models.enums.AgeLimit;

import java.sql.Date;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@Builder
public class Product implements Entity {
    private final String id;
    private final String name;
    private final Date dateOfRelease;
    private String description;
    private AgeLimit ageLimit;
    private Double price;

    public Product(String name, Date dateOfRelease, String description, AgeLimit ageLimit, Double price) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.dateOfRelease = dateOfRelease;
        this.description = description;
        this.ageLimit = ageLimit;
        this.price = price;
    }

    public Integer getAgeLimit() {
        return ageLimit.getValue();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfRelease=" + dateOfRelease +
                ", description='" + description + '\'' +
                ", ageLimit=" + ageLimit.getValue() +
                ", price=" + price +
                '}';
    }
}

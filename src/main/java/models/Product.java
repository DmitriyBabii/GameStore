package models;

import lombok.*;

import java.sql.Date;

@Getter
@AllArgsConstructor
public class Product {
    private final String name;
    private final Date dateOfRelease;
    private Text description;
    private final int ageLimit;
    private int price;

    public void setDescription(Text description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}

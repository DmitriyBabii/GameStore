package models;

import lombok.*;

import java.sql.Date;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Product {
    private final String id = UUID.randomUUID().toString();
    private final String name;
    private final Date dateOfRelease;
    @Getter(AccessLevel.NONE)
    private Text description;
    private final int ageLimit;
    private int price;

    public void setDescription(Text description) {
        this.description = description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description.toString();
    }
}

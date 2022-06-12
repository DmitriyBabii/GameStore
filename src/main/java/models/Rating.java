package models;

import intarfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import models.figures.Client;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Rating implements Entity {
    private final Client client;
    private final Product product;
    private Text review;
    private Date reviewDate;

    public String getReview() {
        return review.toString();
    }

    public void setReview(Text review) {
        this.review = review;
        reviewDate = Date.valueOf(LocalDate.now());
    }
}

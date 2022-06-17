package models;

import intarfaces.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import models.figures.Client;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@Builder
public class Rating implements Entity {
    private final Client client;
    private final Product product;
    private String review;
    private Date reviewDate;

    public String getReview() {
        return review.toString();
    }

    public void setReview(String review) {
        this.review = review;
        reviewDate = Date.valueOf(LocalDate.now());
    }

    @Override
    public String toString() {
        return "Rating{" +
                "client=" + client +
                ", product=" + product +
                ", review='" + review + '\'' +
                ", reviewDate=" + reviewDate +
                '}';
    }
}


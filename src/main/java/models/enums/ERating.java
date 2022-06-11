package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum ERating {
    id_user_fk("CREATE TABLE IF NOT EXISTS `gameshop`.`rating` (\n"
            + "`id_user_fk` VARCHAR(36),\n"),
    id_product_fk("`id_product_fk` VARCHAR(36),\n"),
    review("`review` VARCHAR(255) NOT NULL,\n"),
    review_date("`review_date` DATE NOT NULL,\n"
            + "FOREIGN KEY (id_user_fk)  REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_product_fk)  REFERENCES product (id_product));");

    private final String query;
}

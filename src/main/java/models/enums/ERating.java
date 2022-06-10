package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERating {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`rating` (\n"),
    id_user_fk("`id_user_fk` VARCHAR(36),\n"),
    id_product_fk("`id_product_fk` VARCHAR(36),\n"),
    review("`review` VARCHAR(255) NOT NULL,\n"),
    review_date("`review_date` DATE NOT NULL,\n"),
    foreign_key("FOREIGN KEY (id_user_fk)  REFERENCES user (id_user),\n"
            +"FOREIGN KEY (id_product_fk)  REFERENCES product (id_product)\n"),
    table_end(");");

    private final String query;
}

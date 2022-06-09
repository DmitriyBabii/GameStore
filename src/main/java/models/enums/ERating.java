package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ERating {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`rating` (\n"),
    id_rating("`id_rating` INT NOT NULL AUTO_INCREMENT,\n"),
    id_client_fk("`id_client_fk` INT,\n"),
    id_product_fk("`id_product_fk` INT,\n"),
    review("`review` VARCHAR(255) NOT NULL,\n"),
    review_date("`review_date` DATE NOT NULL,\n"),
    foreign_key("FOREIGN KEY (id_client_fk)  REFERENCES authorized_client (id_client),\n"
            +"FOREIGN KEY (id_product_fk)  REFERENCES product (id_product),\n"),
    table_end("PRIMARY KEY (`id_rating`));");

    private final String query;
}

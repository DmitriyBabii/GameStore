package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EProduct {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`product` (\n"),
    id_product("`id_product` INT NOT NULL AUTO_INCREMENT,\n"),
    name("`name` VARCHAR(30) NOT NULL,\n"),
    date_of_release("`date_of_release` DATE NOT NULL,\n"),
    destination("`destination` VARCHAR(255) NOT NULL,\n"),
    age_limit("`age_limit` INT NOT NULL,\n"),
    price("`price` DOUBLE NOT NULL,\n"),
    table_end("PRIMARY KEY (`id_product`));");

    private final String query;
}

package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EStorage {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`storage` (\n"),
    id_storage("`id_storage` INT NOT NULL AUTO_INCREMENT,\n"),
    id_product_fk("`id_product_fk` INT,\n"),
    quantity("`quantity` INT NOT NULL,\n"),
    foreign_key("FOREIGN KEY (id_product_fk)  REFERENCES product (id_product),\n"),
    table_end("PRIMARY KEY (`id_storage`));");

    private final String query;
}

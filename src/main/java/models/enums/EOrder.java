package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EOrder {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`order` (\n"),
    id_order("`id_order` INT NOT NULL AUTO_INCREMENT,\n"),
    id_manager_fk("`id_manager_fk` INT,\n"),
    id_store_keeper_fk("`id_store_keeper_fk` INT,\n"),
    id_courier_fk("`id_courier_fk` INT,\n"),
    id_product_fk("`id_product_fk` INT,\n"),
    id_client_fk("`id_client_fk` INT,\n"),
    price("`price` INT NOT NULL,\n"),
    start_date("`start_date` DATE NOT NULL,\n"),
    end_date("`end_date` DATE,\n"),
    foreign_key("FOREIGN KEY (id_manager_fk) REFERENCES authorized_client (id_client),\n"
            + "FOREIGN KEY (id_store_keeper_fk) REFERENCES store_keeper (id_store_keeper),\n"
            + "FOREIGN KEY (id_courier_fk) REFERENCES courier (id_courier),\n"
            + "FOREIGN KEY (id_product_fk) REFERENCES product (id_product),\n"
            + "FOREIGN KEY (id_client_fk) REFERENCES authorized_client (id_client),\n"),
    table_end("PRIMARY KEY (`id_order`));");

    private final String query;
}

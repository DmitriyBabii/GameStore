package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EOrder {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`order` (\n"),
    id_order("`id_order` INT NOT NULL AUTO_INCREMENT,\n"),
    id_user_client_fk("`id_user_client_fk` INT,\n"),
    id_user_manager_fk("`id_user_manager_fk` INT,\n"),
    id_user_storekeeper_fk("`id_user_storekeeper_fk` INT,\n"),
    id_user_courier_fk("`id_user_courier_fk` INT,\n"),
    id_product_fk("`id_product_fk` INT,\n"),
    price("`price` DOUBLE NOT NULL,\n"),
    start_date("`start_date` DATE NOT NULL,\n"),
    end_date_storekeeper("`end_date_storekeeper` DATE,\n"),
    end_date_courier("`end_date_courier` DATE,\n"),
    foreign_key("FOREIGN KEY (id_user_client_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_manager_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_storekeeper_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_courier_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_product_fk) REFERENCES product (id_product),\n"),
    table_end("PRIMARY KEY (`id_order`));");

    private final String query;
}

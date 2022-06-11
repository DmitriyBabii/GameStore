package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum EOrder {

    id_order("CREATE TABLE IF NOT EXISTS `gameshop`.`order` (\n"
            + "`id_order` VARCHAR(36) NOT NULL,\n"),
    id_user_client_fk("`id_user_client_fk` VARCHAR(36),\n"),
    id_user_manager_fk("`id_user_manager_fk` VARCHAR(36),\n"),
    id_user_storekeeper_fk("`id_user_storekeeper_fk` VARCHAR(36),\n"),
    id_user_courier_fk("`id_user_courier_fk` VARCHAR(36),\n"),
    id_product_fk("`id_product_fk` VARCHAR(36),\n"),
    price("`price` DOUBLE NOT NULL,\n"),
    start_date("`start_date` DATE NOT NULL,\n"),
    end_date_storekeeper("`end_date_storekeeper` DATE,\n"),
    end_date_courier("`end_date_courier` DATE,\n"
            + "FOREIGN KEY (id_user_client_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_manager_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_storekeeper_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_courier_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_product_fk) REFERENCES product (id_product),\n"
            + "PRIMARY KEY (`id_order`));"
    );

    private final String query;
}

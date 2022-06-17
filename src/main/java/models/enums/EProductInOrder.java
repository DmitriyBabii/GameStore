package models.enums;

import lombok.Getter;

@Getter
public enum EProductInOrder {
    id_order_fk("CREATE TABLE IF NOT EXISTS gameshop.product_in_order (\n"
            + "id_order_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_product_fk("id_product_fk" + MySQLType.ID + MySQLAttribute.NOT_NULL + ",\n"
            + "FOREIGN KEY (id_order_fk) REFERENCES gameshop.order (id_order),\n"
            + "FOREIGN KEY (id_product_fk) REFERENCES gameshop.product (id_product));");

    private final String query;

    EProductInOrder(String query) {
        this.query = query;
    }

    EProductInOrder(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

/**
 * "CREATE TABLE IF NOT EXISTS gameshop.product_in_order (\n" +
 * "id_order_fk VARCHAR(36) NOT NULL,\n" +
 * "id_product_fk VARCHAR(36) NOT NULL,\n" +
 * "FOREIGN KEY (id_product_fk) REFERENCES gameshop.product (id_product),\n" +
 * "FOREIGN KEY (id_order_fk) REFERENCES gameshop.order (id_order));"
 * <p>
 * CREATE TABLE `gameshop`.`new_table` (
 * `idnew_table` INT NULL,
 * `some_id` VARCHAR(45) NULL,
 * `some_another_id` VARCHAR(45) NULL,
 * INDEX `some_id_idx` (`some_id` ASC) VISIBLE,
 * INDEX `some_another_id_idx` (`some_another_id` ASC) VISIBLE,
 * CONSTRAINT `some_id`
 * FOREIGN KEY (`some_id`)
 * REFERENCES `gameshop`.`product` (`id_product`)
 * ON DELETE NO ACTION
 * ON UPDATE NO ACTION,
 * CONSTRAINT `some_another_id`
 * FOREIGN KEY (`some_another_id`)
 * REFERENCES `gameshop`.`order` (`id_order`)
 * ON DELETE NO ACTION
 * ON UPDATE NO ACTION);
 */
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
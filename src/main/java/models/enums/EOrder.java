package models.enums;

import lombok.Getter;


@Getter
public enum EOrder {

    id_order("CREATE TABLE IF NOT EXISTS gameshop.order (\n"
            + "id_order", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_client_fk("id_user_client_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_manager_fk("id_user_manager_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_storekeeper_fk("id_user_storekeeper_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_courier_fk("id_user_courier_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_product_fk("id_product_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    price("price", MySQLType.DOUBLE, MySQLAttribute.NOT_NULL),
    start_date("start_date", MySQLType.DATE, MySQLAttribute.NOT_NULL),
    end_date_storekeeper("end_date_storekeeper", MySQLType.DATE),
    end_date_courier("end_date_courier " + MySQLType.DATE + ",\n"
            + "FOREIGN KEY (id_user_client_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_manager_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_storekeeper_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_courier_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_product_fk) REFERENCES product (id_product),\n"
            + "PRIMARY KEY (`id_order`));"
    );

    private final String query;

    EOrder(String query) {
        this.query = query;
    }

    EOrder(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

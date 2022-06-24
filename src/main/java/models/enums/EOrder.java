package models.enums;

import intarfaces.EntityEnum;
import lombok.Getter;


@Getter
public enum EOrder implements EntityEnum {
    id_order("CREATE TABLE IF NOT EXISTS game_shop.order (\n"
            + "id_order", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_client_fk("id_user_client_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_user_manager_fk("id_user_manager_fk", MySQLType.ID),
    id_user_storekeeper_fk("id_user_storekeeper_fk", MySQLType.ID),
    id_user_courier_fk("id_user_courier_fk", MySQLType.ID),
    price("price", MySQLType.DOUBLE, MySQLAttribute.NOT_NULL),
    start_date("start_date", MySQLType.DATE, MySQLAttribute.NOT_NULL),
    end_date_manager("end_date_manager", MySQLType.DATE),
    end_date_storekeeper("end_date_storekeeper", MySQLType.DATE),
    end_date_courier("end_date_courier " + MySQLType.DATE + ",\n"
            + "FOREIGN KEY (id_user_client_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_manager_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_storekeeper_fk) REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_user_courier_fk) REFERENCES user (id_user),\n"
            + "PRIMARY KEY (id_order));"
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

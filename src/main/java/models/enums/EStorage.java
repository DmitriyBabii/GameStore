package models.enums;

import intarfaces.EntityEnum;
import lombok.Getter;

@Getter
public enum EStorage implements EntityEnum {
    id_storage("CREATE TABLE IF NOT EXISTS gameshop.storage (\n"
            + "id_storage", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_product_fk("id_product_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    quantity("quantity" + MySQLType.INT + MySQLAttribute.NOT_NULL + ",\n"
            + "FOREIGN KEY (id_product_fk)  REFERENCES product (id_product),\n"
            + "PRIMARY KEY (id_storage));");

    private final String query;

    EStorage(String query) {
        this.query = query;
    }

    EStorage(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

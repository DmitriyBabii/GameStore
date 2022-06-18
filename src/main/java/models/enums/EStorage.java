package models.enums;

import intarfaces.EntityEnum;
import lombok.Getter;

@Getter
public enum EStorage implements EntityEnum {
    id_product_fk("CREATE TABLE IF NOT EXISTS gameshop.storage (\n"
            + "id_product_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    quantity("quantity" + MySQLType.INT + MySQLAttribute.NOT_NULL + ",\n"
            + "FOREIGN KEY (id_product_fk)  REFERENCES product (id_product));");

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

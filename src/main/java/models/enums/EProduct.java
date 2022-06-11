package models.enums;

import lombok.Getter;

@Getter
public enum EProduct {
    id_product("CREATE TABLE IF NOT EXISTS gameshop.product (\n"
            + "id_product", MySQLType.ID, MySQLAttribute.NOT_NULL),
    name("name", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL),
    date_of_release("date_of_release", MySQLType.DATE, MySQLAttribute.NOT_NULL),
    destination("destination", MySQLType.LONG_STRING, MySQLAttribute.NOT_NULL),
    age_limit("age_limit", MySQLType.INT, MySQLAttribute.NOT_NULL),
    price("price" + MySQLType.DOUBLE + MySQLAttribute.NOT_NULL + ",\n"
            + "PRIMARY KEY (id_product));");

    private final String query;

    EProduct(String query) {
        this.query = query;
    }

    EProduct(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

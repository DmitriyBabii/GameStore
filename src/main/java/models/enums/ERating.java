package models.enums;

import lombok.Getter;


@Getter
public enum ERating {
    id_user_fk("CREATE TABLE IF NOT EXISTS gameshop.rating (\n"
            + "id_user_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    id_product_fk("id_product_fk", MySQLType.ID, MySQLAttribute.NOT_NULL),
    review("review", MySQLType.LONG_STRING, MySQLAttribute.NOT_NULL),
    review_date("review_date " + MySQLType.DATE + MySQLAttribute.NOT_NULL + ",\n"
            + "FOREIGN KEY (id_user_fk)  REFERENCES user (id_user),\n"
            + "FOREIGN KEY (id_product_fk)  REFERENCES product (id_product));");

    private final String query;

    ERating(String query) {
        this.query = query;
    }

    ERating(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

package models.enums;

import lombok.Getter;


@Getter
public enum EUser {
    id_user("CREATE TABLE IF NOT EXISTS gameshop.user (\n"
            + "id_user", MySQLType.ID, MySQLAttribute.NOT_NULL),
    name("name", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL),
    last_name("last_name", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL),
    username("username", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL, MySQLAttribute.UNIQUE),
    password("password", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL),
    email("email", MySQLType.SHORT_STRING, MySQLAttribute.NOT_NULL, MySQLAttribute.UNIQUE),
    phone_number("phone_number", MySQLType.PHONE_NUMBER, MySQLAttribute.NOT_NULL, MySQLAttribute.UNIQUE),
    date_of_birth("date_of_birth", MySQLType.DATE, MySQLAttribute.NOT_NULL),
    role("role " + MySQLType.SHORT_STRING + MySQLAttribute.NOT_NULL + ",\n"
            + "PRIMARY KEY (id_user));");

    private final String query;

    EUser(String query) {
        this.query = query;
    }

    EUser(String query, MySQLType type, MySQLAttribute... attribute) {
        StringBuilder str = new StringBuilder(query + type);
        for (MySQLAttribute mySQLAttribute : attribute) {
            str.append(mySQLAttribute);
        }
        this.query = str.append(",\n").toString();
    }
}

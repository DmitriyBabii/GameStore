package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EManager {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`manager` (\n"),
    id_manager("`id_manager` INT NOT NULL AUTO_INCREMENT,\n"),
    name("`name` VARCHAR(20) NOT NULL,\n"),
    last_name("`last_name` VARCHAR(20) NOT NULL,\n"),
    username("`username` VARCHAR(30) NOT NULL,\n"),
    password("`password` VARCHAR(30) NOT NULL,\n"),
    email("`email` VARCHAR(30) NOT NULL,\n"),
    phone_number("`phone_number` VARCHAR(13) NOT NULL UNIQUE,\n"),
    date_of_birth("`date_of_birth` DATE NOT NULL,\n"),
    table_end("PRIMARY KEY (`id_manager`));");

    private final String query;
}

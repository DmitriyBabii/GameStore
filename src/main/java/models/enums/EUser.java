package models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EUser {
    table_start("CREATE TABLE IF NOT EXISTS `gameshop`.`user` (\n"),
    id_user("`id_user` VARCHAR(36),\n"),
    name("`name` VARCHAR(20) NOT NULL,\n"),
    last_name("`last_name` VARCHAR(20) NOT NULL,\n"),
    username("`username` VARCHAR(30) NOT NULL UNIQUE,\n"),
    password("`password` VARCHAR(30) NOT NULL,\n"),
    email("`email` VARCHAR(35) NOT NULL UNIQUE,\n"),
    phone_number("`phone_number` VARCHAR(13) NOT NULL UNIQUE,\n"),
    date_of_birth("`date_of_birth` DATE NOT NULL,\n"),
    character("`character` VARCHAR(15) NOT NULL,\n"),
    table_end("PRIMARY KEY (`id_user`));");

    private final String query;
}

package models.figures;

import models.enums.Role;

import java.sql.Date;

public class Client extends AuthorizedUser {
    public Client(String name, String lastName, String username,
                  String password, String phoneNumber, String email, Date birthday) {
        super(name, lastName, username, password, phoneNumber, email, birthday);
        this.role = Role.CLIENT;
    }

    public Client(String id, String name, String lastName, String username,
                  String password, String phoneNumber, String email, Date birthday) {
        super(id, name, lastName, username, password, phoneNumber, email, birthday);
        this.role = Role.CLIENT;
    }
}

package models.figures;

import models.enums.Role;

import java.sql.Date;

public class Courier extends AuthorizedUser {
    private Courier(){

    }

    public Courier(String name, String lastName, String username,
                   String password, String phoneNumber, String email, Date birthday) {
        super(name, lastName, username, password, phoneNumber, email, birthday);
        this.role= Role.COURIER;
    }

    public Courier(String id, String name, String lastName, String username,
                   String password, String phoneNumber, String email, Date birthday) {
        super(id, name, lastName, username, password, phoneNumber, email, birthday);
        this.role= Role.COURIER;
    }
}

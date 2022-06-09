package models.figures;

import java.sql.Date;

public class Storekeeper extends AuthorizedUser {

    public Storekeeper(String name, String lastName, String username, String password,
                       String phoneNumber, String email, Date birthday) {
        super(name, lastName, username, password, phoneNumber, email, birthday);
    }
}

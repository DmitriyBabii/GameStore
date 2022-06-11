package models.figures;


import java.sql.Date;

public class Courier extends AuthorizedUser{
    public Courier(String name, String lastName, String username,
                  String password, String phoneNumber, String email, Date birthday) {
        super(name, lastName, username, password, phoneNumber, email, birthday);
    }

    public Courier(String id, String name, String lastName, String username,
                  String password, String phoneNumber, String email, Date birthday) {
        super(id, name, lastName, username, password, phoneNumber, email, birthday);
    }
}

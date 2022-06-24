package models.figures;

import intarfaces.Entity;
import lombok.*;
import models.enums.Role;

import java.sql.Date;
import java.util.UUID;


@Setter
@Getter
public abstract class AuthorizedUser extends User implements Entity {
    @Setter(AccessLevel.NONE)
    protected String id;
    protected String name;
    protected String lastName;
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String email;
    @Setter(AccessLevel.NONE)
    protected Date birthday;
    @Setter(AccessLevel.NONE)
    protected Role role;

    public AuthorizedUser(String name, String lastName, String username, String password,
                          String phoneNumber, String email, Date birthday) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }

    public AuthorizedUser(String id, String name, String lastName, String username, String password,
                          String phoneNumber, String email, Date birthday) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }

    public String getRole() {
        return role.toString();
    }

    public Role getElementRole() {
        return role;
    }

    @Override
    public String toString() {
        return "AuthorizedUser{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", birthday=" + birthday +
                ", role=" + role +
                '}';
    }
}

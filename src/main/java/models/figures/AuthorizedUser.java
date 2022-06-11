package models.figures;

import intarfaces.Entity;
import lombok.*;
import models.enums.Role;

import java.sql.Date;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
public abstract class AuthorizedUser extends User implements Entity {
    protected final String id;
    protected String name;
    protected String lastName;
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String email;
    protected final Date birthday;
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
}

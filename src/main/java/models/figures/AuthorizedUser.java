package models.figures;

import lombok.*;

import java.sql.Date;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class AuthorizedUser extends User {
    protected final String id;
    protected String name;
    protected String lastName;
    protected String username;
    protected String password;
    protected String phoneNumber;
    protected String email;
    protected final Date birthday;

    public AuthorizedUser(String name, String lastName, String username, String password, String phoneNumber, String email, Date birthday) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.birthday = birthday;
    }
}

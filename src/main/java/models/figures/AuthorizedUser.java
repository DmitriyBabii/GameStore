package models.figures;

import lombok.*;

import java.sql.Date;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class AuthorizedUser extends User {
    private final String id = UUID.randomUUID().toString();
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    @Setter(AccessLevel.NONE)
    private Date birthday;
}

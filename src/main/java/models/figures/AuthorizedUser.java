package models.figures;

import lombok.*;

import java.sql.Date;



@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public abstract class AuthorizedUser extends User {
    private String name;
    private String lastName;
    private String username;
    private String password;
    private String phoneNumber;
    private String email;
    @Setter(AccessLevel.NONE)
    private Date birthday;
}

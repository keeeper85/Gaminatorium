package eu.gaminatorium.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name", unique = true)
    @Size(min = 4, message = "Username must be at least 4 characters long")
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

}

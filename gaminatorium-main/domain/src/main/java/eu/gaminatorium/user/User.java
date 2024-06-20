package eu.gaminatorium.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

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
    private String userName;

    @Column(name = "email")
    @Email
    private String email;

    @Column(name = "password")
    @Min(value = 8)
    private String password;

}

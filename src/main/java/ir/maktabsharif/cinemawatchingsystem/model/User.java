package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.model.base.BaseModel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorValue(value = "user_disc")
@DiscriminatorColumn(
        name = "data_type",
        discriminatorType = DiscriminatorType.STRING,
        columnDefinition = "VARCHAR(35)"
)
@Table(name = "users")
@NoArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(
        callSuper = false,
        exclude = {"profilePicture", "password", "comments"}
)
public class User extends BaseModel<Long> {

    @Column(name = "username", unique = true, nullable = false)
    protected String username;

    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "email", unique = true, nullable = false)
    protected String email;

    @Column(columnDefinition = "TEXT")
    private String profilePicture;
}

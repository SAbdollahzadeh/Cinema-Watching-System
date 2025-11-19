package ir.maktabsharif.cinemawatchingsystem.model;

import ir.maktabsharif.cinemawatchingsystem.enums.AdminLevel;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "admins")
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@DiscriminatorValue(value = "admin")
@SuperBuilder
public class Admin extends User {

    @Enumerated(EnumType.STRING)
    @Column(name = "admin_level")
    private AdminLevel adminLevel;
}
